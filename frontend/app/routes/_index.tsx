import {useActionData, useLoaderData} from "@remix-run/react";
import {ActionFunction, json, LoaderFunction, redirect} from "@remix-run/node";
import {createCompany, deleteCompany, getCompanies, updateCompany} from "~/utils/api";
import {CompanyForm} from "~/components/CompanyForm";
import {CompanyTable} from "~/components/CompanyTable";
import {Company, CompanyRequest} from "~/types/company";
import React, {useState} from "react";

// Loader function to get the list of companies
export const loader: LoaderFunction = async () => {
    const companies = await getCompanies();
    return json(companies);
};

// Validation logic for create and update requests
const validateCompanyData = (data: CompanyRequest) => {
    const errors: { [key: string]: string } = {};

    if (!data.name) errors.name = "Name is required";
    if (!data.industry) errors.industry = "Industry is required";
    if (!data.address.street) errors["address.street"] = "Street is required";
    if (!data.address.postalCode) errors["address.postalCode"] = "PostalCode is required";
    if (!data.address.city) errors["address.city"] = "City is required";
    if (!data.primaryContact.name) errors["primaryContact.name"] = "Primary contact name is required";
    if (!data.primaryContact.email) errors["primaryContact.email"] = "Email is required";
    if (!data.primaryContact.phoneNumber) errors["primaryContact.phoneNumber"] = "Phone number is required";
    if (!data.countryOfOrigin) errors.countryOfOrigin = "Country of origin is required";

    return Object.keys(errors).length ? errors : null;
};

// Action function for handling create, update, and delete actions
export const action: ActionFunction = async ({request}) => {
    const formData = await request.formData();
    const id = formData.get("id") as string | null;
    const method = request.method.toLowerCase();

    // If it's a DELETE request, perform the delete action without validation
    if (method === "delete" && id) {
        await deleteCompany(id);
        return redirect("/");
    }

    // For POST or PUT (create or update), extract and validate the form data
    const companyData: CompanyRequest = {
        name: formData.get("name") as string,
        industry: formData.get("industry") as string,
        address: {
            street: formData.get("address.street") as string,
            postalCode: formData.get("address.postalCode") as string,
            city: formData.get("address.city") as string,
        },
        primaryContact: {
            name: formData.get("primaryContact.name") as string,
            email: formData.get("primaryContact.email") as string,
            phoneNumber: formData.get("primaryContact.phoneNumber") as string,
        },
        countryOfOrigin: formData.get("countryOfOrigin") as string,
        notes: formData.get("notes") as string,
    };

    // Apply validation only for create and update (POST/PUT)
    const errors = validateCompanyData(companyData);
    if (errors) {
        return json({errors, companyData}, {status: 400});
    }

    try {
        if (id) {
            // Update company
            await updateCompany(id, companyData);
        } else {
            // Create new company
            await createCompany(companyData);
        }
        return json({success: true});
    } catch (error: any) {
        return json({error: error.message}, {status: 400});
    }
};

export default function Index() {
    const companies = useLoaderData<Company[]>();
    const actionData = useActionData<{
        errors?: { [key: string]: string };
        companyData?: CompanyRequest;
        success?: boolean;
    }>();
    const [editingCompany, setEditingCompany] = useState<Company | null>(null);
    const [errors, setErrors] = useState<Record<string, string>>({});

    const handleEdit = (company: Company) => {
        setEditingCompany(company);
    };

    // Set errors if any
    const handleFormErrors = () => {
        if (actionData?.errors) {
            setErrors(actionData.errors);
        }
        if (actionData?.success) {
            setEditingCompany(null); // Clear editing company on successful submission
        }
    };

    // Call handleFormErrors when actionData changes
    React.useEffect(() => {
        handleFormErrors();
    }, [actionData]);

    return (
        <div>
            <h1>Company List</h1>
            <CompanyForm company={editingCompany} errors={errors}/>
            <CompanyTable companies={companies} onEdit={handleEdit}/>
        </div>
    );
}
