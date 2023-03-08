public class OrgNrData {
    public OrgNrData(String input, String longFormat, String shortFormat, boolean valid, String type, String vatNumber) {
        this.input = input;
        this.longFormat = longFormat;
        this.shortFormat = shortFormat;
        this.valid = valid;
        this.type = type;
        this.vatNumber = vatNumber;
    }

    public String input;
    public String longFormat;
    public String shortFormat;
    public boolean valid;
    public String type;
    public String vatNumber;
}
