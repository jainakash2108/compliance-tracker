package com.auditor.compliance.tracker.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.sql.DriverManager

@TestConfiguration
class PostgresContainerConfiguration {
    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> = container

    companion object {
        private val container: PostgreSQLContainer<*> =
            PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
                .withDatabaseName("compliance-tracker")
                .withInitScript("db/schema-init.sql")

        fun truncateTables() {
            val connection = DriverManager.getConnection(
                container.jdbcUrl,
                container.username,
                container.password,
            )

            val allTables = "address, contact, company"
            connection.prepareStatement("TRUNCATE TABLE $allTables RESTART IDENTITY CASCADE").execute()
        }
    }
}