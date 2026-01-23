package com.toy.first_come_grant_service.infra.adapter.`in`.web.dto

enum class ClaimResultCode(val message: String) {
    SUCCESS("지급 성공"),
    SOLD_OUT("재고 소진"),
    ALREADY_CLAIMED("이미 참여함"),
    NOT_ACTIVE("이벤트가 활성 상태가 아님"),
    EVENT_NOT_FOUND("이벤트를 찾을 수 없음"),
    INVALID_REQUEST("잘못된 요청")
}
