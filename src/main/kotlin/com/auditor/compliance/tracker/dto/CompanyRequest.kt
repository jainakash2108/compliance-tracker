package com.auditor.compliance.tracker.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class CompanyRequest(
    @field:NotBlank(message = "Company name cannot be blank")
    val name: String,

    @field:NotBlank(message = "Industry cannot be blank")
    val industry: String,

    val address: AddressRequest,

    val primaryContact: PrimaryContactRequest,

    @field:NotBlank(message = "Country of origin cannot be blank")
    val countryOfOrigin: String,

    val notes: String? = null
)

data class AddressRequest(
    @field:NotBlank(message = "Street cannot be blank")
    val street: String,

    @field:NotBlank(message = "Postal code cannot be blank")
    val postalCode: String,

    @field:NotBlank(message = "City cannot be blank")
    val city: String
)

data class PrimaryContactRequest(
    @field:NotBlank(message = "Primary contact name cannot be blank")
    val name: String,

    @field:Email(message = "Email should be valid")
    val email: String,

    @field:Pattern(
        regexp = "^\\+?[0-9]{7,15}\$",
        message = "Phone number should be valid and contain only digits, and can optionally start with a '+'"
    )
    val phoneNumber: String
)
