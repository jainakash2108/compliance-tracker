package com.auditor.compliance.tracker.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class Configuration {

    init {
        enableHyphenCharacterInSchemaNameForLiquibase()
    }

    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()

}

fun enableHyphenCharacterInSchemaNameForLiquibase() {
    System.setProperty("liquibase.preserveSchemaCase", "true")
}