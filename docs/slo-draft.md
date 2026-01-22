# SLO 정의

## 용어 정의

- **SLI (Service Level Indicator)**: 측정 가능한 서비스 품질 지표
- **SLO (Service Level Objective)**: SLI의 목표 값
- **Error Budget**: 허용 가능한 실패 비율

## 실패 정의

다음 중 하나라도 해당하면 실패:
- HTTP 5xx 응답
- 응답 시간 > 3000ms (timeout)
- 커넥션 실패/거부

## SLO 목표

| 지표 | 목표 | 측정 윈도우 |
|------|------|-------------|
| Availability | 99.95% | 5분 rolling |
| Claim p95 | ≤ 200ms | - |
| Claim p99 | ≤ 500ms | - |
| View p95 | ≤ 100ms | - |
| View p99 | ≤ 250ms | - |

## Error Budget

| 기간 | 허용 다운타임 | 계산 |
|------|---------------|------|
| 월간 | 21.6분 | 30일 × 24시간 × 60분 × 0.05% |
| 주간 | 5분 | 7일 × 24시간 × 60분 × 0.05% |

## 성공/실패 판정 (한 문장)

> **5분 윈도우 내 성공률이 99.95% 이상이고, Claim p95 ≤ 200ms, View p95 ≤ 100ms이면 SLO 충족**

## k6 Threshold (코드화)
```javascript
export const options = {
  thresholds: {
    // 전체 에러율
    http_req_failed: ['rate<0.0005'],
    
    // Claim 엔드포인트
    'http_req_duration{endpoint:claim}': ['p(95)<200', 'p(99)<500'],
    
    // View 엔드포인트
    'http_req_duration{endpoint:view}': ['p(95)<100', 'p(99)<250'],
  },
};
```

## 알림 기준 (향후 적용)

| 수준 | 조건 | 액션 |
|------|------|------|
| Warning | Error Budget 50% 소진 | Slack 알림 |
| Critical | Error Budget 80% 소진 | 배포 중단, 온콜 호출 |
