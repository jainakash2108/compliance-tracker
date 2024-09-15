package com.auditor.compliance.tracker.config

import java.util.*

fun String.assertUUID(): UUID {
    return try {
        UUID.fromString(this)
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Invalid id provided: $this", e)
    }
}