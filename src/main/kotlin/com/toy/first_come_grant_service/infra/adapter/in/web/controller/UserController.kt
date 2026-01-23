package com.toy.first_come_grant_service.infra.adapter.`in`.web.controller

import com.toy.first_come_grant_service.infra.adapter.`in`.web.dto.UserCouponResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "User", description = "사용자 API")
@RestController
@RequestMapping("/v1/users")
class UserController {

    @Operation(summary = "내 쿠폰 목록 조회", description = "사용자가 발급받은 쿠폰 목록을 조회한다")
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "조회 성공")
    )
    @GetMapping("/{userId}/coupons")
    fun getUserCoupons(
        @Parameter(description = "사용자 ID", example = "user-001")
        @PathVariable userId: String,

        @Parameter(description = "이벤트 ID 필터 (선택)", example = "1")
        @RequestParam(required = false) eventId: Long?
    ): ResponseEntity<List<UserCouponResponse>> {
        // W01-T04에서 구현
        TODO("Not implemented")
    }
}
