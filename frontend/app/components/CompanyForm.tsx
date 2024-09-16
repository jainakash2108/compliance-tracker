import {Form} from "@remix-run/react";
import {CompanyRequest} from "~/types/company";

interface CompanyFormProps {
    company?: CompanyRequest | null;
    errors?: Record<string, string>;
}

export function CompanyForm({company, errors}: CompanyFormProps) {
    // Ensure these values are correctly matching the options available
    const defaultIndustry = company?.industry || "";
    const defaultCountryOfOrigin = company?.countryOfOrigin || "";

    return (
        <div>
            {errors && (
                <div>
                    <ul style={{color: "red"}}>
                        {Object.keys(errors).map((errorKey) => (
                            <li key={errorKey}>{errors[errorKey]}</li>
                        ))}
                    </ul>
                </div>
            )}
            <Form method="post">
                <input type="hidden" name="id" value={company?.id || ""}/>

                <table>
                    <tbody>
                    <tr>
                        <td>Name:</td>
                        <td>
                            <input
                                type="text"
                                name="name"
                                defaultValue={company?.name || ""}
                            />
                        </td>
                    </tr>

                    <tr>
                        <td>Industry:</td>
                        <td>
                            <select name="industry" defaultValue={defaultIndustry}>
                                <option value="" disabled>Select an Industry</option>
                                <option value="TECHNOLOGY">Technology</option>
                                <option value="FINANCE">Finance</option>
                                <option value="HEALTHCARE">Healthcare</option>
                                <option value="EDUCATION">Education</option>
                                <option value="MANUFACTURING">Manufacturing</option>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td>Address</td>
                        <td>
                            <fieldset>
                                <legend>Address</legend>
                                <tr>
                                    <td>
                                        <div>
                                            <label>
                                                Street:
                                                <input
                                                    type="text"
                                                    name="address.street"
                                                    defaultValue={company?.address?.street || ""}
                                                />
                                            </label>
                                        </div>
                                    </td>
                                    <td>
                                        <div>
                                            <label>
                                                Postal Code:
                                                <input
                                                    type="text"
                                                    name="address.postalCode"
                                                    defaultValue={company?.address?.postalCode || ""}
                                                />
                                            </label>
                                        </div>
                                    </td>
                                    <td>
                                        <div>
                                            <label>
                                                City:
                                                <input
                                                    type="text"
                                                    name="address.city"
                                                    defaultValue={company?.address?.city || ""}
                                                />
                                            </label>
                                        </div>
                                    </td>
                                </tr>
                            </fieldset>
                        </td>
                    </tr>

                    <tr>
                        <td>Primary Contact</td>
                        <td>
                            <fieldset>
                                <legend>Primary Contact</legend>
                                <tr>
                                    <td>
                                        <div>
                                            <label>
                                                Name:
                                                <input
                                                    type="text"
                                                    name="primaryContact.name"
                                                    defaultValue={company?.primaryContact?.name || ""}
                                                />
                                            </label>
                                        </div>
                                    </td>
                                    <td>
                                        <div>
                                            <label>
                                                Email:
                                                <input
                                                    type="email"
                                                    name="primaryContact.email"
                                                    defaultValue={company?.primaryContact?.email || ""}
                                                />
                                            </label>
                                        </div>
                                    </td>
                                    <td>
                                        <div>
                                            <label>
                                                Phone Number:
                                                <input
                                                    type="text"
                                                    name="primaryContact.phoneNumber"
                                                    defaultValue={company?.primaryContact?.phoneNumber || ""}
                                                />
                                            </label>
                                        </div>
                                    </td>
                                </tr>
                            </fieldset>
                        </td>
                    </tr>

                    <tr>
                        <td>Country of Origin:</td>
                        <td>
                            <select name="countryOfOrigin" defaultValue={defaultCountryOfOrigin}>
                                <option value="" disabled>Select a Country</option>
                                <option value="NORWAY">Norway</option>
                                <option value="SWEDEN">Sweden</option>
                                <option value="DENMARK">Denmark</option>
                            </select>
                        </td>
                    </tr>

                    <tr>
                        <td>Notes:</td>
                        <td>
                            <textarea name="notes" defaultValue={company?.notes || ""}></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <button type="submit">{company ? "Update" : "Create"}</button>
            </Form>
        </div>
    );
}
