package com.auditor.compliance.tracker.dto

data class CompanyResponse(
    val id: String,
    val name: String,
    val industry: String,
    val address: AddressResponse,
    val primaryContact: PrimaryContactResponse,
    val countryOfOrigin: String,
    val notes: String?
)

data class AddressResponse(
    val street: String,
    val postalCode: String,
    val city: String
)

data class PrimaryContactResponse(
    val name: String,
    val email: String,
    val phoneNumber: String
)

