package com.auditor.compliance.tracker.repository

import com.auditor.compliance.tracker.domain.Address
import com.auditor.compliance.tracker.domain.Company
import com.auditor.compliance.tracker.domain.Contact
import com.auditor.compliance.tracker.domain.CountryOfOrigin
import com.auditor.compliance.tracker.domain.Email
import com.auditor.compliance.tracker.domain.Industry
import com.auditor.compliance.tracker.domain.PhoneNumber
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.util.*

@Repository
class CompanyDao(private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) {

    private val rowMapper = RowMapper { rs: ResultSet, _: Int ->
        Company(
            id = UUID.fromString(rs.getString("company_id")),
            name = rs.getString("company_name"),
            industry = Industry.valueOf(rs.getString("industry")),
            address = Address(
                street = rs.getString("street"),
                postalCode = rs.getString("postal_code"),
                city = rs.getString("city")
            ),
            primaryContact = Contact(
                name = rs.getString("contact_name"),
                email = Email.of(rs.getString("contact_email")),
                phoneNumber = PhoneNumber(rs.getString("contact_phone_number"))
            ),
            countryOfOrigin = CountryOfOrigin.valueOf(rs.getString("country_of_origin")),
            notes = rs.getString("notes")
        )
    }

    fun selectAll(): List<Company> {
        val sql = """
            SELECT c.id            AS company_id,
                   c.name          AS company_name,
                   c.industry,
                   a.street,
                   a.postal_code,
                   a.city,
                   co.name         AS contact_name,
                   co.email        AS contact_email,
                   co.phone_number AS contact_phone_number,
                   c.country_of_origin,
                   c.notes
            FROM company c
                     JOIN address a ON c.address_id = a.id
                     JOIN contact co ON c.contact_id = co.id
        """.trimIndent()

        return namedParameterJdbcTemplate.query(sql, rowMapper)
    }

    fun selectById(id: UUID): Company? {
        val sql = """
        SELECT c.id            AS company_id,
               c.name          AS company_name,
               c.industry,
               a.street,
               a.postal_code,
               a.city,
               co.name         AS contact_name,
               co.email        AS contact_email,
               co.phone_number AS contact_phone_number,
               c.country_of_origin,
               c.notes
        FROM company c
                 JOIN address a ON c.address_id = a.id
                 JOIN contact co ON c.contact_id = co.id
        WHERE c.id = :id
    """.trimIndent()
        val params = mapOf("id" to id)
        return try {
            namedParameterJdbcTemplate.queryForObject(sql, params, rowMapper)
        } catch (e: EmptyResultDataAccessException) {
            null
        }
    }

    fun insert(company: Company): Int {
        val addressId = UUID.randomUUID()
        val addressSql = """
            INSERT INTO address (id, street, postal_code, city) 
            VALUES (:id, :street, :postalCode, :city)
        """.trimIndent()
        val addressParams = mapOf(
            "id" to addressId,
            "street" to company.address.street,
            "postalCode" to company.address.postalCode,
            "city" to company.address.city
        )
        namedParameterJdbcTemplate.update(addressSql, addressParams)

        val contactId = UUID.randomUUID()
        val contactSql = """
            INSERT INTO contact (id, name, email, phone_number) 
            VALUES (:id, :name, :email, :phoneNumber)
        """.trimIndent()
        val contactParams = mapOf(
            "id" to contactId,
            "name" to company.primaryContact.name,
            "email" to company.primaryContact.email.value,
            "phoneNumber" to company.primaryContact.phoneNumber.value
        )
        namedParameterJdbcTemplate.update(contactSql, contactParams)

        val companySql = """
            INSERT INTO company (id, name, industry, address_id, contact_id, country_of_origin, notes) 
            VALUES (:id, :name, :industry, :addressId, :contactId, :countryOfOrigin, :notes)
        """.trimIndent()
        val companyParams = mapOf(
            "id" to UUID.randomUUID(),
            "name" to company.name,
            "industry" to company.industry.name,
            "addressId" to addressId,
            "contactId" to contactId,
            "countryOfOrigin" to company.countryOfOrigin.name,
            "notes" to company.notes
        )
        return namedParameterJdbcTemplate.update(companySql, companyParams)
    }

    fun update(id: UUID, company: Company): Int {
        val addressSql = """
            UPDATE address
            SET street = :street, postal_code = :postalCode, city = :city
            WHERE id = (SELECT address_id FROM company WHERE id = :id)
        """.trimIndent()
        val addressParams = mapOf(
            "street" to company.address.street,
            "postalCode" to company.address.postalCode,
            "city" to company.address.city,
            "id" to id
        )
        namedParameterJdbcTemplate.update(addressSql, addressParams)

        val contactSql = """
            UPDATE contact
            SET name = :name, email = :email, phone_number = :phoneNumber
            WHERE id = (SELECT contact_id FROM company WHERE id = :id)
        """.trimIndent()
        val contactParams = mapOf(
            "name" to company.primaryContact.name,
            "email" to company.primaryContact.email.value,
            "phoneNumber" to company.primaryContact.phoneNumber.value,
            "id" to id
        )
        namedParameterJdbcTemplate.update(contactSql, contactParams)

        val companySql = """
            UPDATE company
            SET name = :name, industry = :industry, country_of_origin = :countryOfOrigin, notes = :notes
            WHERE id = :id
        """.trimIndent()
        val companyParams = mapOf(
            "name" to company.name,
            "industry" to company.industry.name,
            "countryOfOrigin" to company.countryOfOrigin.name,
            "notes" to company.notes,
            "id" to id
        )
        return namedParameterJdbcTemplate.update(companySql, companyParams)
    }

    fun delete(id: UUID): Int {
        val sql = """
            DELETE FROM company WHERE id = :id
        """.trimIndent()
        val params = mapOf("id" to id)
        return namedParameterJdbcTemplate.update(sql, params)
    }
}
