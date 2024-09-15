package com.auditor.compliance.tracker.controller

import com.auditor.compliance.tracker.objects.mockCompany
import com.auditor.compliance.tracker.objects.mockCompanyRequest
import com.auditor.compliance.tracker.objects.mockPrimaryContactRequest
import com.auditor.compliance.tracker.service.CompanyService
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.springframework.http.HttpStatus
import java.util.*

class CompanyControllerTest : DescribeSpec({

    val companyService = mockk<CompanyService>(relaxed = true)
    val controller = CompanyController(companyService)

    describe("getAllCompanies") {
        it("should return all companies") {
            val companies = listOf(mockCompany, mockCompany)
            every { companyService.findAll() } returns companies

            val response = controller.getAllCompanies()

            response.statusCode shouldBe HttpStatus.OK
            response.body!!.size shouldBe companies.size
        }
    }

    describe("getCompanyById") {
        it("should return a company by ID") {
            val company = mockCompany
            every { companyService.findById(any()) } returns company

            val response = controller.getCompanyById(UUID.randomUUID().toString())

            response.statusCode shouldBe HttpStatus.OK
            response.body!!.id shouldBe company.id
        }

        it("should return 404 if company not found") {
            every { companyService.findById(any()) } returns null

            val response = controller.getCompanyById(UUID.randomUUID().toString())

            response.statusCode shouldBe HttpStatus.NOT_FOUND
        }

        it("should return 400 for invalid UUID") {
            shouldThrow<IllegalArgumentException> {
                controller.getCompanyById("invalid-uuid")
            }.message shouldBe "Invalid id provided: invalid-uuid"
        }
    }

    describe("createCompany") {
        it("should create and return a new company") {
            val companyRequest = mockCompanyRequest
            val company = mockCompany
            every { companyService.create(any()) } returns company

            val response = controller.createCompany(companyRequest)

            response.statusCode shouldBe HttpStatus.CREATED
            response.body!!.name shouldBe company.name
        }

        it("should return multiple validation errors for invalid CompanyRequest fields") {
            shouldThrow<IllegalArgumentException> {
                controller.createCompany(mockCompanyRequest.copy(industry = ""))
            }.message shouldBe "Invalid Industry value: "

            shouldThrow<IllegalArgumentException> {
                controller.createCompany(mockCompanyRequest.copy(countryOfOrigin = ""))
            }.message shouldBe "Invalid CountryOfOrigin value: "

            shouldThrow<IllegalArgumentException> {
                controller.createCompany(mockCompanyRequest.copy(primaryContact = mockPrimaryContactRequest.copy(email = "")))
            }.message shouldBe "Email cannot be blank"

            shouldThrow<IllegalArgumentException> {
                controller.createCompany(mockCompanyRequest.copy(primaryContact = mockPrimaryContactRequest.copy(email = "test@sgfsg")))
            }.message shouldBe "Invalid email provided: test@sgfsg"

            shouldThrow<IllegalArgumentException> {
                controller.createCompany(
                    mockCompanyRequest.copy(
                        primaryContact = mockPrimaryContactRequest.copy(
                            phoneNumber = "3567894"
                        )
                    )
                )
            }.message shouldBe "Invalid phone number provided: 3567894"
        }
    }

    describe("updateCompany") {
        it("should update and return the company") {
            val companyRequest = mockCompanyRequest
            val company = mockCompany
            every { companyService.update(any(), any()) } returns company

            val response = controller.updateCompany(UUID.randomUUID().toString(), companyRequest)

            response.statusCode shouldBe HttpStatus.OK
            response.body!!.name shouldBe company.name
        }

        it("should return 404 if company not found") {
            every { companyService.update(any(), any()) } returns null

            val response = controller.updateCompany(UUID.randomUUID().toString(), mockCompanyRequest)

            response.statusCode shouldBe HttpStatus.NOT_FOUND
        }

        it("should return 400 for invalid UUID") {
            shouldThrow<IllegalArgumentException> {
                controller.updateCompany("invalid-uuid", mockCompanyRequest)
            }.message shouldBe "Invalid id provided: invalid-uuid"
        }
    }

    describe("deleteCompany") {
        it("should delete and return no content") {
            every { companyService.delete(any()) } returns true

            val response = controller.deleteCompany(UUID.randomUUID().toString())

            response.statusCode shouldBe HttpStatus.NO_CONTENT
        }

        it("should return 404 if company not found") {
            every { companyService.delete(any()) } returns false

            val response = controller.deleteCompany(UUID.randomUUID().toString())

            response.statusCode shouldBe HttpStatus.NOT_FOUND
        }

        it("should return 400 for invalid UUID") {
            shouldThrow<IllegalArgumentException> {
                controller.deleteCompany("invalid-uuid")
            }.message shouldBe "Invalid id provided: invalid-uuid"
        }
    }
})
