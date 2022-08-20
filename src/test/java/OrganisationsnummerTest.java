import dev.organisationsnummer.Organisationsnummer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class OrganisationsnummerTest {

    @ParameterizedTest
    @CsvSource({
            "556016-0680,true",
            "556103-4249,true",
            "5561034249,true",
            "556016-0681,false",
            "556103-4250,false",
            "5561034250,false"
    })
    public void testValidateOrgNumbers(String number, boolean valid) {
        assertEquals(valid, Organisationsnummer.valid(number));
    }

    @ParameterizedTest
    @CsvSource({
            "556016-0680,5560160680",
            "556103-4249,5561034249",
            "5561034249,5561034249",
            "901211-9948,9012119948",
            "9012119948,9012119948",
    })
    public void testFormatWithoutSeparator(String number, String expected) {
        assertEquals(expected, Organisationsnummer.parse(number).format(false));

    }

    @ParameterizedTest
    @CsvSource({
            "556016-0680,556016-0680",
            "556103-4249,556103-4249",
            "5561034249,556103-4249",
            "9012119948,901211-9948",
            "901211-9948,901211-9948",
    })
    public void testFormatWithSeparator(String number, String expected) {
        assertEquals(expected, Organisationsnummer.parse(number).format(true));
    }

    @ParameterizedTest
    @CsvSource({
            "556016-0680,Aktiebolag",
            "556103-4249,Aktiebolag",
            "5561034249,Aktiebolag",
            "8510033999,Enskild firma",
    })
    public void testType(String number, String expected) {
        assertEquals(expected, Organisationsnummer.parse(number).type());
    }

    @ParameterizedTest
    @CsvSource({
            "556016-0680,SE556016068001",
            "556103-4249,SE556103424901",
            "5561034249,SE556103424901",
            "9012119948,SE901211994801",
            "19901211-9948,SE901211994801",
    })
    public void testVat(String number, String expected) {
        assertEquals(expected, Organisationsnummer.parse(number).vatNumber());
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "121212121212",
        "121212-1212",
    })
    public void testWithPersonnummer(String ssn) {
        Organisationsnummer nr = Organisationsnummer.parse(ssn);
        assertTrue(nr.isPersonnummer());
        assertEquals("Enskild firma", nr.type());
    }
}
