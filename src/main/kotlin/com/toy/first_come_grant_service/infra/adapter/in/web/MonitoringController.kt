package com.toy.first_come_grant_service.infra.adapter.`in`.web

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger { }

@RestController
class MonitoringController {

    @GetMapping("/ready")
    fun ready(): ResponseEntity<Map<String, String>> {
        logger.info { "Readiness check requested" }
        // 나중에 DB/Redis/Kafka 체크 추가
        return ResponseEntity.ok(mapOf("status" to "UP"))
    }
}
