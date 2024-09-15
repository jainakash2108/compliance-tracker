package com.auditor.compliance.tracker.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfiguration {

    @Bean
    fun api(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Compliance Tracker")
                .description("Application that allows Compliance Officers to manage and track company compliance information")
        )
        .addServersItem(
            Server()
                .url("http://localhost:8080/compliance-tracker")
                .description("Compliance Tracker in Dev/Local"),
        )
}