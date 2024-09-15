package com.auditor.compliance.tracker.exception

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
    val path: String,
    val timestamp: String
) {
    companion object {
        private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        fun create(
            status: Int,
            error: String,
            message: String,
            path: String
        ): ErrorResponse {
            return ErrorResponse(
                status = status,
                error = error,
                message = message,
                path = path,
                timestamp = LocalDateTime.now().format(dateTimeFormatter)
            )
        }
    }
}
