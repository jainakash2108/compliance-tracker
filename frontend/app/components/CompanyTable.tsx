import { Form } from "@remix-run/react";
import { Company } from "~/types/company";

interface CompanyTableProps {
    companies: Company[];
    onEdit: (company: Company) => void; // Add an onEdit callback
}

export function CompanyTable({ companies, onEdit }: CompanyTableProps) {
    if (companies.length === 0) {
        return <p>No companies available</p>;
    }

    return (
        <table style={{ borderCollapse: 'collapse', width: '100%' }}>
            <thead>
            <tr style={{ backgroundColor: '#f2f2f2' }}>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Name</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Industry</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Street</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Postal Code</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>City</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Contact Name</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Contact Email</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Contact Phone</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Country of Origin</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Notes</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Edit</th>
                <th style={{ border: '1px solid #ddd', padding: '8px' }}>Delete</th>
            </tr>
            </thead>
            <tbody>
            {companies.map((company) => (
                <tr key={company.id}>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.name}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.industry}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.address.street}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.address.postalCode}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.address.city}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.primaryContact.name}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.primaryContact.email}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.primaryContact.phoneNumber}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.countryOfOrigin}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>{company.notes}</td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>
                        <button type="button" onClick={() => onEdit(company)}>
                            Edit
                        </button>
                    </td>
                    <td style={{ border: '1px solid #ddd', padding: '8px' }}>
                        <Form method="delete">
                            <input type="hidden" name="id" value={company.id} />
                            <button type="submit">Delete</button>
                        </Form>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}
