package com.auditor.compliance.tracker.controller

import com.auditor.compliance.tracker.config.assertUUID
import com.auditor.compliance.tracker.domain.Address
import com.auditor.compliance.tracker.domain.Company
import com.auditor.compliance.tracker.domain.Contact
import com.auditor.compliance.tracker.domain.CountryOfOrigin
import com.auditor.compliance.tracker.domain.Email
import com.auditor.compliance.tracker.domain.Industry
import com.auditor.compliance.tracker.domain.PhoneNumber
import com.auditor.compliance.tracker.dto.AddressResponse
import com.auditor.compliance.tracker.dto.CompanyRequest
import com.auditor.compliance.tracker.dto.CompanyResponse
import com.auditor.compliance.tracker.dto.PrimaryContactResponse
import com.auditor.compliance.tracker.service.CompanyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/companies")
class CompanyController(private val companyService: CompanyService) {

    @GetMapping
    fun getAllCompanies(): ResponseEntity<List<CompanyResponse>> =
        ResponseEntity.ok(companyService.findAll().map { company: Company -> company.toResponse() })

    @GetMapping("/{id}")
    fun getCompanyById(@PathVariable id: String): ResponseEntity<CompanyResponse> {
        val uuid: UUID = id.assertUUID()
        val company = companyService.findById(uuid) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(company.toResponse())
    }

    @PostMapping
    fun createCompany(@Valid @RequestBody companyRequest: CompanyRequest): ResponseEntity<CompanyResponse> {
        val company = companyRequest.toDomain()
        val savedCompany = companyService.create(company)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompany.toResponse())
    }

    @PutMapping("/{id}")
    fun updateCompany(
        @PathVariable id: String,
        @Valid @RequestBody updatedCompanyRequest: CompanyRequest
    ): ResponseEntity<CompanyResponse> {
        val uuid: UUID = id.assertUUID()
        val updatedCompany = updatedCompanyRequest.toDomain()
        val company = companyService.update(uuid, updatedCompany) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(company.toResponse())
    }

    @DeleteMapping("/{id}")
    fun deleteCompany(@PathVariable id: String): ResponseEntity<Void> {
        val uuid: UUID = id.assertUUID()
        return if (companyService.delete(uuid)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}

private fun CompanyRequest.toDomain(): Company = Company(
    name = this.name,
    industry = Industry.fromString(this.industry),
    address = Address(
        street = this.address.street,
        postalCode = this.address.postalCode,
        city = this.address.city
    ),
    primaryContact = Contact(
        name = this.primaryContact.name,
        email = Email.of(this.primaryContact.email),
        phoneNumber = PhoneNumber(this.primaryContact.phoneNumber)
    ),
    countryOfOrigin = CountryOfOrigin.fromString(this.countryOfOrigin),
    notes = this.notes
)

private fun Company.toResponse(): CompanyResponse {
    return CompanyResponse(
        id = this.id ?: throw RuntimeException("Id should not be null"),
        name = this.name,
        industry = this.industry.name,
        address = AddressResponse(
            street = this.address.street,
            postalCode = this.address.postalCode,
            city = this.address.city
        ),
        primaryContact = PrimaryContactResponse(
            name = this.primaryContact.name,
            email = this.primaryContact.email.value,
            phoneNumber = this.primaryContact.phoneNumber.asE164
        ),
        countryOfOrigin = this.countryOfOrigin.name,
        notes = this.notes
    )
}
