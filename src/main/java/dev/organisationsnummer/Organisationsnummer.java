package dev.organisationsnummer;

import dev.personnummer.*;
public class Organisationsnummer {

    public static Organisationsnummer parse(String input) { return new Organisationsnummer(input); }
    public static boolean valid(String input) { return false; }

    public Organisationsnummer(String input) {}

    public String vatNumber() { return ""; }

    public String format(boolean separator) { return ""; }

    public String format() { return this.format(true); }

    public boolean isPersonnummer() { return false; }

    public Personnummer personnummer() throws PersonnummerException { return new Personnummer(""); }

    public String type() { return ""; }
}
