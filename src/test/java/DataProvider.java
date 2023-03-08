import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataProvider {

    private static final List<OrgNrData> data = new ArrayList<>();

    public static void initialize() throws IOException {
        InputStream in = new URL("https://raw.githubusercontent.com/organisationsnummer/meta/main/testdata/list.json").openStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String json = "", line = "";
        while ((line = reader.readLine()) != null) {
            json = json.concat(line);
        }

        JSONArray rootObject = new JSONArray(json);
        for (int i = 0; i < rootObject.length(); i++) {
            JSONObject current = rootObject.getJSONObject(i);
            data.add(new OrgNrData(
                    current.getString("input"),
                    current.getString("long_format"),
                    current.getString("short_format"),
                    current.getBoolean("valid"),
                    current.getString("type"),
                    current.getString("vat_number")

            ));
        }
    }

    public static List<OrgNrData> getValid() {
        return data.stream().filter(o -> o.valid).collect(Collectors.toList());
    }

    public static List<OrgNrData> getInvalid() {
        return data.stream().filter(o -> !o.valid).collect(Collectors.toList());
    }

    public static List<OrgNrData> getAll() {
        return data;
    }

}
