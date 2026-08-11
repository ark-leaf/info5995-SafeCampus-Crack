package au.edu.safecampus.connect.network;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AlertResponseParser {
    public List<String> parseTitles(String body) {
        List<String> titles = new ArrayList<>();
        if (body == null || body.trim().isEmpty()) {
            return titles;
        }
        for (String line : body.split("\\n")) {
            String value = line.trim();
            if (!value.isEmpty()) {
                titles.add(value);
            }
        }
        return titles;
    }
}
