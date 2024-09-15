package com.auditor.compliance.tracker.domain

import org.apache.commons.validator.routines.EmailValidator

data class Email(val value: String) {

    companion object {
        private val validator: EmailValidator = EmailValidator.getInstance()

        fun of(email: String): Email {
            require(email.isNotBlank()) { "Email cannot be blank" }
            require(isValidEmail(email)) { "Invalid email format: $email" }
            return Email(email)
        }

        private fun isValidEmail(email: String): Boolean {
            return validator.isValid(email)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Email

        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()

    override fun toString(): String = "Email(value='$value')"
}


