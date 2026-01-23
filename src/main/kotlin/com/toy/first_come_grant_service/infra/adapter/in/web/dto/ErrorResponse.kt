package com.toy.first_come_grant_service.infra.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "에러 응답")
data class ErrorResponse(
    @Schema(description = "에러 코드", example = "SOLD_OUT")
    val errorCode: String,

    @Schema(description = "에러 메시지", example = "재고가 소진되었습니다")
    val message: String,

    @Schema(description = "상세 정보", example = "eventId=123, remainingQuantity=0")
    val detail: String? = null
)
