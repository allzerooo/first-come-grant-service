package com.toy.first_come_grant_service.infra.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "선착순 참여 응답")
data class ClaimResponse(
    @Schema(description = "결과 코드", example = "SUCCESS")
    val resultCode: ClaimResultCode,

    @Schema(description = "결과 메시지", example = "지급 성공")
    val message: String,

    @Schema(description = "발급된 쿠폰 코드 (성공 시)", example = "COUPON-ABC123")
    val couponCode: String? = null
)

@Schema(description = "내 쿠폰 조회 응답")
data class UserCouponResponse(
    @Schema(description = "쿠폰 ID", example = "1")
    val id: Long,

    @Schema(description = "이벤트 ID", example = "1")
    val eventId: Long,

    @Schema(description = "이벤트 이름", example = "신규 가입 쿠폰 이벤트")
    val eventName: String,

    @Schema(description = "쿠폰 코드", example = "COUPON-ABC123")
    val couponCode: String,

    @Schema(description = "발급 시각")
    val issuedAt: String
)
