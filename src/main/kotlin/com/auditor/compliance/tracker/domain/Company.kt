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

enum class Industry {
    TECHNOLOGY, FINANCE, HEALTHCARE, EDUCATION, MANUFACTURING, OTHER
}

enum class CountryOfOrigin {
    NORWAY, SWEDEN, DENMARK, OTHER
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
