-- Event 테이블
CREATE TABLE event (
                       id              BIGSERIAL PRIMARY KEY,
                       name            VARCHAR(200) NOT NULL,
                       status          VARCHAR(20) NOT NULL DEFAULT 'DRAFT',
                       total_quantity  INTEGER NOT NULL,
                       remaining_quantity INTEGER NOT NULL,
                       start_at        TIMESTAMP WITH TIME ZONE NOT NULL,
                       end_at          TIMESTAMP WITH TIME ZONE NOT NULL,
                       created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
                       updated_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),

                       CONSTRAINT chk_event_status CHECK (status IN ('DRAFT', 'ACTIVE', 'PAUSED', 'ENDED')),
                       CONSTRAINT chk_quantity_non_negative CHECK (remaining_quantity >= 0),
                       CONSTRAINT chk_quantity_valid CHECK (remaining_quantity <= total_quantity),
                       CONSTRAINT chk_date_range CHECK (start_at < end_at)
);

-- Claim 테이블 (참여 기록)
CREATE TABLE claim (
                       id              BIGSERIAL PRIMARY KEY,
                       event_id        BIGINT NOT NULL REFERENCES event(id),
                       user_id         VARCHAR(100) NOT NULL,
                       idempotency_key VARCHAR(100) NOT NULL,
                       result_code     VARCHAR(30) NOT NULL,
                       coupon_code     VARCHAR(50),  -- 성공 시에만 NOT NULL
                       created_at      TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),

                       CONSTRAINT chk_result_code CHECK (result_code IN (
                                                                         'SUCCESS', 'SOLD_OUT', 'ALREADY_CLAIMED', 'NOT_ACTIVE', 'EVENT_NOT_FOUND', 'INVALID_REQUEST'
                           ))
);

-- 핵심 유니크 제약: 1인 1회 참여
CREATE UNIQUE INDEX uk_claim_event_user
    ON claim(event_id, user_id)
    WHERE result_code = 'SUCCESS';

-- 멱등성 보장: 동일 요청 중복 방지
CREATE UNIQUE INDEX uk_claim_event_idempotency
    ON claim(event_id, idempotency_key);

-- 조회 패턴: 내 쿠폰 목록 (최신순)
CREATE INDEX idx_claim_user_created
    ON claim(user_id, created_at DESC);

-- 조회 패턴: 이벤트별 참여 현황
CREATE INDEX idx_claim_event_result
    ON claim(event_id, result_code);

COMMENT ON TABLE event IS '선착순 지급 이벤트';
COMMENT ON TABLE claim IS '선착순 참여 기록 (성공/실패 모두 저장)';
COMMENT ON INDEX uk_claim_event_user IS '1인 1회 참여 제약 (성공한 경우만)';
COMMENT ON INDEX uk_claim_event_idempotency IS '네트워크 재시도 흡수용 멱등키';
