package com.auditor.compliance.tracker.domain

import java.util.*

data class Company(
    val id: UUID? = null,
    val name: String,
    val industry: Industry,
    val address: Address,
    val primaryContact: Contact,
    val countryOfOrigin: CountryOfOrigin,
    val notes: String? = null
)

sealed class Industry(val name: String) {
    data object TECHNOLOGY : Industry("TECHNOLOGY")
    data object FINANCE : Industry("FINANCE")
    data object HEALTHCARE : Industry("HEALTHCARE")
    data object EDUCATION : Industry("EDUCATION")
    data object MANUFACTURING : Industry("MANUFACTURING")

    companion object {
        fun fromString(name: String): Industry {
            return when (name.uppercase()) {
                "TECHNOLOGY" -> TECHNOLOGY
                "FINANCE" -> FINANCE
                "HEALTHCARE" -> HEALTHCARE
                "EDUCATION" -> EDUCATION
                "MANUFACTURING" -> MANUFACTURING
                else -> throw IllegalArgumentException("Invalid Industry value: $name")
            }
        }
    }
}

sealed class CountryOfOrigin(val name: String) {
    data object NORWAY : CountryOfOrigin("NORWAY")
    data object SWEDEN : CountryOfOrigin("SWEDEN")
    data object DENMARK : CountryOfOrigin("DENMARK")

    companion object {
        fun fromString(name: String): CountryOfOrigin {
            return when (name.uppercase()) {
                "NORWAY" -> NORWAY
                "SWEDEN" -> SWEDEN
                "DENMARK" -> DENMARK
                else -> throw IllegalArgumentException("Invalid CountryOfOrigin value: $name")
            }
        }
    }
}

data class Contact(
    val name: String,
    val email: Email,
    val phoneNumber: PhoneNumber
)

data class Address(
    val street: String,
    val postalCode: String,
    val city: String
)
