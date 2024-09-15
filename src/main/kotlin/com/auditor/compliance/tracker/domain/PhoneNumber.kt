package com.auditor.compliance.tracker.domain

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil

data class PhoneNumber(val value: String) {

    companion object {
        private val phoneNumberUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
        private const val DEFAULT_REGION = "NO"
    }

    val asE164: String by lazy {
        try {
            phoneNumberUtil
                .parse(value, DEFAULT_REGION)
                .let { phoneNumber ->
                    phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
                }
        } catch (e: NumberParseException) {
            throw IllegalArgumentException("Invalid phone number: $value", e)
        }
    }

    init {
        require(isValidPhoneNumber(value)) { "Invalid phone number format: $value" }
    }

    private fun isValidPhoneNumber(phone: String): Boolean {
        return try {
            val parsed = phoneNumberUtil.parse(phone, DEFAULT_REGION)
            phoneNumberUtil.isValidNumber(parsed)
        } catch (e: NumberParseException) {
            false
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhoneNumber

        return asE164 == other.asE164
    }

    override fun hashCode(): Int = asE164.hashCode()

    override fun toString(): String = "PhoneNumber(asE164='$asE164')"
}
