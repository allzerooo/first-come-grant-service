# First Come Grant Service - 도메인 정의

## 유비쿼터스 언어

### Event (이벤트/캠페인)
선착순 지급이 일어나는 단위. 쿠폰/포인트/경품 등 구체적 보상과 무관하게 "한정 수량을 먼저 온 사람에게 지급"하는 행위의 컨테이너.

| 용어 | 정의 |
|------|------|
| Event | 선착순 지급 캠페인 |
| totalQuantity | 전체 지급 가능 수량 |
| remainingQuantity | 남은 수량 |
| startAt | 참여 시작 시각 |
| endAt | 참여 종료 시각 |

### Claim (참여/신청)
사용자가 Event에 참여하는 행위. 성공하면 보상이 지급되고, 실패하면 사유와 함께 거절된다.

| 용어 | 정의 |
|------|------|
| Claim | 선착순 참여 요청 |
| Coupon/Grant | Claim 성공 시 발급되는 보상 기록 |
| Ledger | 지급 내역 원장 (감사/추적용) |

---

## Event 상태 전이
```
DRAFT ──(activate)──▶ ACTIVE ──(pause)──▶ PAUSED
  │                      │                   │
  │                      │    (resume)◀──────┘
  │                      │
  │                      ▼
  └────────────────▶  ENDED
        (end)           ▲
                        │
              (자동: endAt 도달 또는 remainingQuantity = 0)
```

| 상태 | 설명 | Claim 가능 |
|------|------|-----------|
| DRAFT | 생성됨, 아직 시작 안 함 | ❌ |
| ACTIVE | 진행 중 | ✅ |
| PAUSED | 일시 중지 (운영자 개입) | ❌ |
| ENDED | 종료됨 (기간 만료/품절/수동 종료) | ❌ |

---

## Claim 결과 코드

| 코드 | HTTP Status | 설명 | SLI 집계 |
|------|-------------|------|----------|
| SUCCESS | 200 | 지급 성공 | ✅ 성공 |
| SOLD_OUT | 200 | 품절 (정상 비즈니스 결과) | ✅ 성공 |
| ALREADY_CLAIMED | 200 | 이미 참여함 (중복 방지) | ✅ 성공 |
| NOT_ACTIVE | 400 | 이벤트가 ACTIVE 상태가 아님 | ✅ 성공 |
| EVENT_NOT_FOUND | 404 | 이벤트 없음 | ✅ 성공 |
| INVALID_REQUEST | 400 | 필수 파라미터 누락 등 | ✅ 성공 |
| THROTTLED | 429 | 요청 제한 초과 | ⚠️ 별도 집계 |
| INTERNAL_ERROR | 500 | 시스템 오류 | ❌ 실패 |
| TIMEOUT | 504 | 응답 시간 초과 (>3000ms) | ❌ 실패 |

### SLI 집계 기준
- **성공**: 2xx/4xx 응답 (비즈니스 로직이 정상 수행됨)
- **실패**: 5xx 또는 timeout (시스템 장애)
- **THROTTLED**: 별도 대시보드에서 추적 (의도된 보호 동작)

---

## 실패 케이스 체크리스트

### 비즈니스 실패 (정상 동작)
- [ ] 품절 (remainingQuantity = 0)
- [ ] 이미 참여함 (동일 user + event 조합)
- [ ] 기간 아님 (now < startAt 또는 now > endAt)
- [ ] 이벤트 상태가 ACTIVE 아님

### 시스템 실패 (장애)
- [ ] DB 커넥션 실패
- [ ] Redis 커넥션 실패
- [ ] Kafka 발행 실패 (비동기 처리 시)
- [ ] 내부 타임아웃

### 동시성 관련 (W02에서 상세화)
- [ ] 재고 차감 경쟁 → 음수 방지
- [ ] 중복 요청 (같은 Idempotency-Key)
- [ ] 분산 락 획득 실패

---

## 엔티티 관계 (개념)
```
Event (1) ◀───────── (N) Claim/Coupon
           event_id
```

### Claim/Coupon 레코드
| 필드 | 설명 |
|------|------|
| id | PK |
| eventId | FK → Event |
| userId | 참여자 식별자 |
| idempotencyKey | 중복 요청 방지 키 |
| status | ISSUED / REVOKED |
| issuedAt | 발급 시각 |

### 유니크 제약 (후보)
- `(eventId, userId)` → 1인 1회 참여
- `(eventId, idempotencyKey)` → 네트워크 재시도 흡수

---
** Claim: "사용자가 권리를 주장/수령한다"는 의미
** Claim 엔티티 = 참여 + 결과(Coupon): 지금은 하나로 합쳐서 시작. 쿠폰 라이프사이클이 복잡해지면 그때 분리.
