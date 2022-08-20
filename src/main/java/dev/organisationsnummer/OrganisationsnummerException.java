package dev.organisationsnummer;

public class OrganisationsnummerException extends Exception {
    OrganisationsnummerException() {
        this("Invalid Swedish organization number");
    }

    OrganisationsnummerException(String message) {
        super(message);
    }
}
