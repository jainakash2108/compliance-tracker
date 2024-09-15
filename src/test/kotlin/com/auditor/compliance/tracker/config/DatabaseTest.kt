package com.auditor.compliance.tracker.config

import io.kotest.core.spec.style.StringSpec
import io.kotest.core.test.TestCase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.test.context.ContextConfiguration

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = [PostgresContainerConfiguration::class])
class DatabaseTest(body: StringSpec.() -> Unit = {}) : StringSpec(body) {

    override suspend fun beforeEach(testCase: TestCase) {
        PostgresContainerConfiguration.truncateTables()
    }
}