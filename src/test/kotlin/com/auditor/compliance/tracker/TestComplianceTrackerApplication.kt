package com.auditor.compliance.tracker

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<ComplianceTrackerApplication>().with(TestcontainersConfiguration::class).run(*args)
}
