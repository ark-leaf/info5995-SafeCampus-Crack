package au.edu.safecampus.connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class ResponseParser {
    public String readText(HttpsURLConnection connection) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                builder.append(line).append('\n');
            } else {
                reader.close();
                return builder.toString().trim();
            }
        }
    }

    public List<CampusAlert> parseAlerts(String response) {
        List<CampusAlert> alerts = new ArrayList<>();
        if (response == null || response.trim().length() == 0) {
            return alerts;
        }
        String[] rows = response.split("\\n");
        for (String row : rows) {
            String text = row.trim();
            if (text.length() > 0) {
                alerts.add(new CampusAlert("Campus update", text, System.currentTimeMillis()));
            }
        }
        return alerts;
    }
}
