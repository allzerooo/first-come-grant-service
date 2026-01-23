package com.toy.first_come_grant_service.infra.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.OffsetDateTime

@Schema(description = "이벤트 조회 응답")
data class EventResponse(
    @Schema(description = "이벤트 ID", example = "1")
    val id: Long,

    @Schema(description = "이벤트 이름", example = "신규 가입 쿠폰 이벤트")
    val name: String,

    @Schema(description = "상태", example = "ACTIVE")
    val status: String,

    @Schema(description = "전체 수량", example = "1000")
    val totalQuantity: Int,

    @Schema(description = "남은 수량", example = "523")
    val remainingQuantity: Int,

    @Schema(description = "시작 시각")
    val startAt: OffsetDateTime,

    @Schema(description = "종료 시각")
    val endAt: OffsetDateTime
)
