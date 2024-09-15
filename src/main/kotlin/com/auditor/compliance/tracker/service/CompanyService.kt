package com.auditor.compliance.tracker.service

import com.auditor.compliance.tracker.domain.Company
import com.auditor.compliance.tracker.repository.CompanyDao
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CompanyService(private val companyDao: CompanyDao) {

    fun findAll(): List<Company> = companyDao.selectAll()

    fun findById(id: UUID): Company? = companyDao.selectById(id)

    @Transactional
    fun create(company: Company): Company {
        companyDao.insert(company)
        return company.copy(id = UUID.randomUUID())
    }

    @Transactional
    fun update(id: UUID, updatedCompany: Company): Company? {
        return if (companyDao.update(id, updatedCompany) > 0) {
            updatedCompany.copy(id = id)
        } else {
            null
        }
    }

    @Transactional
    fun delete(id: UUID): Boolean = companyDao.delete(id) > 0
}
