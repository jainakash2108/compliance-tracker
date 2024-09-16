import {CompanyRequest} from "~/types/company";

const API_BASE_URL = 'http://localhost:8080/compliance-tracker/api/v1/companies';

export async function getCompanies() {
    const response = await fetch(API_BASE_URL);
    return response.json();
}

export async function getCompany(id: string) {
    const response = await fetch(`${API_BASE_URL}/${id}`);
    return response.json();
}

export async function createCompany(data: CompanyRequest) {
    const response = await fetch(API_BASE_URL, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Failed to create company');
    }

    return await response.json();
}

export async function updateCompany(id: string, data: CompanyRequest) {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data),
    });

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Failed to update company');
    }

    return await response.json();
}

export async function deleteCompany(id: string) {
    const response = await fetch(`${API_BASE_URL}/${id}`, {
        method: 'DELETE',
    });

    if (response.status === 204) {
        return; // No content, so no need to parse the body
    }

    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message || 'Failed to delete company');
    }

    return await response.json();
}
