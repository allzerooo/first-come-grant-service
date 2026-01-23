package com.toy.first_come_grant_service.infra.adapter.`in`.web.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.UUID

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RequestIdFilter : OncePerRequestFilter() {

    companion object {
        const val REQUEST_ID_KEY = "requestId"
        const val REQUEST_ID_HEADER = "X-Request-Id"
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestId = request.getHeader(REQUEST_ID_HEADER) ?: UUID.randomUUID().toString().substring(0, 8)

        try {
            MDC.put(REQUEST_ID_KEY, requestId)
            response.setHeader(REQUEST_ID_HEADER, requestId)
            filterChain.doFilter(request, response)
        } finally {
            MDC.remove(REQUEST_ID_KEY)
        }
    }
}
