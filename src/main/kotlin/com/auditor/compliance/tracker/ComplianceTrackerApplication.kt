package com.auditor.compliance.tracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ComplianceTrackerApplication

fun main(args: Array<String>) {
	runApplication<ComplianceTrackerApplication>(*args)
}
