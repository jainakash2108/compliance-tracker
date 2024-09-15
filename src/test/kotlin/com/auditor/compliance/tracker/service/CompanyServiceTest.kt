package com.auditor.compliance.tracker.service

import com.auditor.compliance.tracker.objects.company
import com.auditor.compliance.tracker.repository.CompanyDao
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*

class CompanyServiceTest : StringSpec({

    val companyDao = mockk<CompanyDao>(relaxed = true)
    val companyService = CompanyService(companyDao)

    "findAll should return a list of companies" {
        val companies = listOf(
            company.copy(name = "Company A"),
            company.copy(name = "Company B"),
        )
        every { companyDao.selectAll() } returns companies

        val result = companyService.findAll()

        result shouldBe companies
        verify { companyDao.selectAll() }
    }

    "findById should return a company by ID" {
        val companyId = UUID.randomUUID()
        val company = company.copy(name = "Company A")
        every { companyDao.selectById(companyId) } returns company

        val result = companyService.findById(companyId)

        result shouldBe company
        verify { companyDao.selectById(companyId) }
    }

    "create should insert a company and return it" {
        val company = company.copy(name = "Company A")
        every { companyDao.insert(company) } returns company

        val result = companyService.create(company)

        result shouldBe company
        verify { companyDao.insert(company) }
    }

    "update should modify a company and return it if updated" {
        val companyId = UUID.randomUUID()
        val updatedCompany = company.copy(name = "Company A")
        every { companyDao.update(companyId, updatedCompany) } returns 1

        val result = companyService.update(companyId, updatedCompany)

        result shouldBe updatedCompany.copy(id = companyId)
        verify { companyDao.update(companyId, updatedCompany) }
    }

    "update should return null if no company was updated" {
        val companyId = UUID.randomUUID()
        val updatedCompany = company.copy(name = "Company A")
        every { companyDao.update(companyId, updatedCompany) } returns 0

        val result = companyService.update(companyId, updatedCompany)

        result shouldBe null
        verify { companyDao.update(companyId, updatedCompany) }
    }

    "delete should return true if company was deleted" {
        val companyId = UUID.randomUUID()
        every { companyDao.delete(companyId) } returns 1

        val result = companyService.delete(companyId)

        result shouldBe true
        verify { companyDao.delete(companyId) }
    }

    "delete should return false if company was not deleted" {
        val companyId = UUID.randomUUID()
        every { companyDao.delete(companyId) } returns 0

        val result = companyService.delete(companyId)

        result shouldBe false
        verify { companyDao.delete(companyId) }
    }
})
