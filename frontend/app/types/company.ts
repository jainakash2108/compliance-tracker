export interface Address {
    street: string;
    postalCode: string;
    city: string;
}

export interface PrimaryContact {
    name: string;
    email: string;
    phoneNumber: string;
}

export interface Company {
    id: string;
    name: string;
    industry: string;
    address: Address;
    primaryContact: PrimaryContact;
    countryOfOrigin: string;
    notes: string;
}

export interface CompanyRequest {
    name: string;
    industry: string;
    address: Address;
    primaryContact: PrimaryContact;
    countryOfOrigin: string;
    notes: string;
    id?: string; // Optional for new entries
}
