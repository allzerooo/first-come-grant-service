package com.toy.first_come_grant_service.infra.adapter.`in`.web.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("First Come Grant Service API")
                    .description("선착순 지급 시스템 API")
                    .version("v1")
            )
            .servers(listOf(Server().url("http://localhost:8080")))
    }
}
