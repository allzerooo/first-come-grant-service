package com.toy.first_come_grant_service.infra.adapter.`in`.web.controller

import com.toy.first_come_grant_service.infra.adapter.`in`.web.dto.ClaimResponse
import com.toy.first_come_grant_service.infra.adapter.`in`.web.dto.ErrorResponse
import com.toy.first_come_grant_service.infra.adapter.`in`.web.dto.EventResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Event", description = "이벤트 API")
@RestController
@RequestMapping("/v1/events")
class EventController {

    @Operation(summary = "이벤트 조회", description = "이벤트 상세 정보를 조회한다")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 성공"),
        ApiResponse(
            responseCode = "404",
            description = "이벤트 없음",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]
        )
    )
    @GetMapping("/{eventId}")
    fun getEvent(
        @Parameter(description = "이벤트 ID", example = "1")
        @PathVariable eventId: Long
    ): ResponseEntity<EventResponse> {
        // W01-T04에서 구현
        TODO("Not implemented")
    }

    @Operation(
        summary = "선착순 참여",
        description = "이벤트에 선착순으로 참여한다. Idempotency-Key 헤더 필수."
    )
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "참여 처리 완료 (성공/품절/이미참여 등)"),
        ApiResponse(
            responseCode = "400",
            description = "잘못된 요청",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "이벤트 없음",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]
        ),
        ApiResponse(
            responseCode = "429",
            description = "요청 제한 초과",
            content = [Content(schema = Schema(implementation = ErrorResponse::class))]
        )
    )
    @PostMapping("/{eventId}/claims")
    fun claim(
        @Parameter(description = "이벤트 ID", example = "1")
        @PathVariable eventId: Long,

        @Parameter(description = "사용자 ID", required = true, example = "user-001")
        @RequestHeader("X-User-Id") userId: String,

        @Parameter(description = "멱등성 키 (중복 요청 방지)", required = true, example = "req-uuid-123")
        @RequestHeader("Idempotency-Key") idempotencyKey: String
    ): ResponseEntity<ClaimResponse> {
        // W01-T04에서 구현
        TODO("Not implemented")
    }
}
