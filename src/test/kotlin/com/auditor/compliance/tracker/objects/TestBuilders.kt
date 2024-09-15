package com.auditor.compliance.tracker.objects

import com.auditor.compliance.tracker.domain.Address
import com.auditor.compliance.tracker.domain.Company
import com.auditor.compliance.tracker.domain.Contact
import com.auditor.compliance.tracker.domain.CountryOfOrigin
import com.auditor.compliance.tracker.domain.Email
import com.auditor.compliance.tracker.domain.Industry
import com.auditor.compliance.tracker.domain.PhoneNumber
import com.auditor.compliance.tracker.dto.AddressRequest
import com.auditor.compliance.tracker.dto.CompanyRequest
import com.auditor.compliance.tracker.dto.PrimaryContactRequest
import java.util.*

val address = Address(
    street = "street 1",
    postalCode = "3563",
    city = "city"
)
val contact = Contact(
    name = "name",
    email = Email.of("test@test.ci"),
    phoneNumber = PhoneNumber("+4723434323")
)

val company = Company(
    id = UUID.randomUUID(),
    name = "name",
    industry = Industry.FINANCE,
    address = address,
    primaryContact = contact,
    countryOfOrigin = CountryOfOrigin.NORWAY,
    notes = "Test"
)

val mockCompany: Company = Company(
    id = UUID.randomUUID(),
    name = "Test Company",
    industry = Industry.TECHNOLOGY,
    address = Address("123 Street", "12345", "Test City"),
    primaryContact = Contact("John Doe", Email.of("john@example.com"), PhoneNumber("+4723434323")),
    countryOfOrigin = CountryOfOrigin.NORWAY,
    notes = "Test notes"
)

val mockAddressRequest: AddressRequest =
    AddressRequest(
        street = "123 Street",
        postalCode = "12345",
        city = "Test City"
    )

val mockPrimaryContactRequest: PrimaryContactRequest =
    PrimaryContactRequest(
        name = "John Doe",
        email = "john@example.com",
        phoneNumber = "+4723434323"
    )

val mockCompanyRequest: CompanyRequest =
    CompanyRequest(
        name = "Test Company",
        industry = "TECHNOLOGY",
        address = mockAddressRequest,
        primaryContact = mockPrimaryContactRequest,
        countryOfOrigin = "NORWAY",
        notes = "Test notes"
    )