package dev.organisationsnummer;

import dev.personnummer.Personnummer;
import dev.personnummer.PersonnummerException;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Organisationsnummer implements Comparable<Organisationsnummer> {

    private static final Pattern regexPattern;
    private static final String[] FIRMA_TYPES = new String[] {
            // String to map different company types.
            // Will only pick 0-9, but we use 10 to be EF as we want it constant.
            "Okänt", // 0
            "Dödsbon", // 1
            "Stat, landsting, kommun eller församling", // 2
            "Utländska företag som bedriver näringsverksamhet eller äger fastigheter i Sverige", // 3
            "Okänt", // 4
            "Aktiebolag", // 5
            "Enkelt bolag", // 6
            "Ekonomisk förening eller bostadsrättsförening", // 7
            "Ideella förening och stiftelse", // 8
            "Handelsbolag, kommanditbolag och enkelt bolag", // 9
            "Enskild firma", // 10
    };

    static {
        regexPattern = Pattern.compile("^(\\d{2}){0,1}(\\d{2})(\\d{2})(\\d{2})([-+]?)?(\\d{4})$");
    }

    private String number;
    private Personnummer innerPersonnummer = null;
    private boolean isPersonnummer = false;


    /**
     * Parse a string to an organisationsnummer.
     *
     * @param input Organisationsnummer as a string to base the object on.
     * @return Organisationsnummer as an object.
     * @throws OrganisationsnummerException On parse error.
     */
    public static Organisationsnummer parse(String input) throws OrganisationsnummerException { return new Organisationsnummer(input); }

    /**
     * Validates a string as a organisationsnummer.
     *
     * @param input Organisationsnummer as a string to validate.
     * @return True on valid organisationsnummer.
     */
    public static boolean valid(String input) {
        try {
            Organisationsnummer.parse(input);
            return true;
        } catch (OrganisationsnummerException e) {
            return false;
        }
    }

    /**
     * Constructor.
     *
     * @param input Organisationsnummer as a string.
     * @throws OrganisationsnummerException On parse error.
     */
    public Organisationsnummer(String input) throws OrganisationsnummerException {
        this.innerParse(input);
    }

    /**
     * Get organisation VAT.
     *
     * @return VAT number of the organization.
     */
    public String vatNumber() { return "SE" + this.getShortString() + "01"; }

    /**
     * Format the Organisationsnummer and return it as a string.
     *
     * @param separator If to include separator or not (default true).
     * @return Formatted string.
     */
    public String format(boolean separator) {
        String nr = this.getShortString();

        return separator ?
                nr.substring(0, 6) + "-" + nr.substring(6) :
                nr;
    }

    /**
     * Format the Organisationsnummer and return it as a string.
     *
     * @return Formatted string.
     */
    public String format() { return this.format(true); }

    /**
     * Determine is the organisationsnummer is a personnummer or not.
     * @return Boolean
     */
    public boolean isPersonnummer() { return this.isPersonnummer; }

    /**
     * Get personnummer instance (if IsPersonnummer).
     * @return Personnummer
     */
    public Personnummer personnummer() { return this.innerPersonnummer; }

    /**
     * Get type of company/firm.
     *
     * @return String
     */
    public String type() {
        return this.isPersonnummer() ?
                Organisationsnummer.FIRMA_TYPES[10] :
                Organisationsnummer.FIRMA_TYPES[Integer.parseInt(this.number.substring(0, 1))];
    }

    @Override
    public String toString() {
        return this.format();
    }

    @Override
    public int hashCode() {
        return this.format(true).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Organisationsnummer other = (Organisationsnummer) obj;
        return Objects.equals(other.format(true), this.format(true));
    }

    @Override
    public int compareTo(Organisationsnummer other) {
        return this.format(true).compareTo(other.format(true));
    }

    private void innerParse(String input) throws OrganisationsnummerException {
        if (input.length() > 13 || input.length() < 10) {
            throw new OrganisationsnummerException("Input value too " + (input.length() > 13 ? "long" : "short"));
        }

        try {
            Matcher matches = regexPattern.matcher(input);
            if (!matches.find()) {
                throw new OrganisationsnummerException();
            }

            if (matches.group(1) != null) {
                if (Integer.parseInt(matches.group(1)) != 16) {
                    throw new OrganisationsnummerException();
                }
                input = input.substring(2);
            }

            input = input.replace("-", "");
            input = input.replace("+", "");


            if (Integer.parseInt(matches.group(3)) < 20 || Integer.parseInt(matches.group(2)) < 10 || !this.lunCheck(input)) {
                throw new OrganisationsnummerException();
            }

            this.number = input;

        } catch (OrganisationsnummerException e) {
            try {
                this.innerPersonnummer = Personnummer.parse(input);
                this.isPersonnummer = true;
            } catch (PersonnummerException ex) {
                throw new OrganisationsnummerException();
            }
        }
    }

    private boolean lunCheck(String input) {

        // Luhn/mod10 algorithm. Used to calculate a checksum from the
        // passed value. The checksum is returned and tested against the control number
        // in the personal identity number to make sure that it is a valid number.

        int temp;
        int sum = 0;

        for (int i = 0; i < input.length(); i++) {
            temp = Character.getNumericValue(input.charAt(i));
            temp *= 2 - (i % 2);
            if (temp > 9)
                temp -= 9;

            sum += temp;
        }

        return sum % 10 == 0;
    }

    private String getShortString() {
        String asString = this.isPersonnummer() ? this.innerPersonnummer.format(false) : this.number;
        asString = asString.replace("-", "");
        return asString.replace("+", "");
    }
}
