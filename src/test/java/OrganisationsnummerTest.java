import dev.organisationsnummer.Organisationsnummer;
import dev.organisationsnummer.OrganisationsnummerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class OrganisationsnummerTest {

    @BeforeAll
    public static void setup() throws IOException {
        DataProvider.initialize();
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getInvalid")
    public void testThrowOnInvalid(OrgNrData input) {
        assertThrows(OrganisationsnummerException.class, () -> {
            Organisationsnummer.parse(input.shortFormat);
        });

        assertThrows(OrganisationsnummerException.class, () -> {
            Organisationsnummer.parse(input.longFormat);
        });
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getAll")
    public void testValidateOrgNumbers(OrgNrData input) {
        assertEquals(input.valid, Organisationsnummer.valid(input.longFormat));
        assertEquals(input.valid, Organisationsnummer.valid(input.shortFormat));
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValid")
    public void testFormatWithoutSeparator(OrgNrData input) throws OrganisationsnummerException {
        assertEquals(input.shortFormat, Organisationsnummer.parse(input.shortFormat).format(false));
        assertEquals(input.shortFormat, Organisationsnummer.parse(input.longFormat).format(false));
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValid")
    public void testFormatWithSeparator(OrgNrData input) throws OrganisationsnummerException {
        assertEquals(input.longFormat.replace('+', '-'), Organisationsnummer.parse(input.shortFormat).format(true));
        assertEquals(input.longFormat, Organisationsnummer.parse(input.longFormat).format(true));
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValid")
    public void testType(OrgNrData input) throws OrganisationsnummerException {
        assertEquals(input.type, Organisationsnummer.parse(input.shortFormat).type());
        assertEquals(input.type, Organisationsnummer.parse(input.longFormat).type());
    }

    @ParameterizedTest
    @MethodSource("DataProvider#getValid")
    public void testVat(OrgNrData input) throws OrganisationsnummerException {
        assertEquals(input.vatNumber, Organisationsnummer.parse(input.shortFormat).vatNumber());
        assertEquals(input.vatNumber, Organisationsnummer.parse(input.longFormat).vatNumber());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "121212121212",
        "121212-1212",
    })
    public void testWithPersonnummer(String ssn) throws OrganisationsnummerException {
        Organisationsnummer nr = Organisationsnummer.parse(ssn);
        assertTrue(nr.isPersonnummer());
        assertEquals("Enskild firma", nr.type());
    }
}
