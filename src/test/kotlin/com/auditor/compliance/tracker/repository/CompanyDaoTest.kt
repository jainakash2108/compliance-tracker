package com.auditor.compliance.tracker.repository

import com.auditor.compliance.tracker.config.DatabaseTest
import com.auditor.compliance.tracker.objects.company
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.util.*

internal class CompanyDaoTest(namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : DatabaseTest({

    val companyDao = CompanyDao(namedParameterJdbcTemplate)

    "Should insert company and retrieve it" {
        val savedCompany = companyDao.insert(company)
        val retrieveCompany = companyDao.selectById(savedCompany.id!!)

        retrieveCompany shouldNotBe null
        retrieveCompany?.id shouldBe savedCompany.id
        retrieveCompany?.name shouldBe savedCompany.name
        retrieveCompany?.industry shouldBe savedCompany.industry
        retrieveCompany?.address?.street shouldBe savedCompany.address.street
        retrieveCompany?.address?.city shouldBe savedCompany.address.city
        retrieveCompany?.address?.postalCode shouldBe savedCompany.address.postalCode
        retrieveCompany?.primaryContact?.name shouldBe savedCompany.primaryContact.name
        retrieveCompany?.primaryContact?.email shouldBe savedCompany.primaryContact.email
        retrieveCompany?.primaryContact?.phoneNumber shouldBe savedCompany.primaryContact.phoneNumber
        retrieveCompany?.countryOfOrigin shouldBe savedCompany.countryOfOrigin
        retrieveCompany?.notes shouldBe savedCompany.notes
    }

    "Should update company details" {
        val savedCompany = companyDao.insert(company)

        val updatedCompany = company.copy(name = "Updated Name", notes = "Updated Notes")
        companyDao.update(savedCompany.id!!, updatedCompany)

        val retrieveCompany = companyDao.selectById(savedCompany.id!!)
        retrieveCompany?.name shouldBe updatedCompany.name
        retrieveCompany?.notes shouldBe updatedCompany.notes
    }

    "Should delete company" {
        val savedCompany = companyDao.insert(company)

        companyDao.delete(savedCompany.id!!)

        val retrieveCompany = companyDao.selectById(savedCompany.id!!)
        retrieveCompany shouldBe null
    }

    "Should select all companies" {
        val company1 = company
        val company2 = company.copy(name = "Another Company")
        companyDao.insert(company1)
        companyDao.insert(company2)

        val companies = companyDao.selectAll()
        companies.size shouldBe 2
        companies.map { it.name } shouldContainExactly listOf(company1.name, company2.name)
    }

    "Should handle company not found" {
        val retrieveCompany = companyDao.selectById(UUID.randomUUID())
        retrieveCompany shouldBe null
    }
})