package au.edu.safecampus.connect.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class JsonParser {
    public Map<String, String> parseFlatObject(String json) {
        Map<String, String> values = new LinkedHashMap<>();
        if (json == null) {
            return values;
        }
        try {
            Type type = new TypeToken<Map<String, String>>() { // from class: au.edu.safecampus.connect.utils.JsonParser.1
            }.getType();
            Map<String, String> parsed = (Map) new Gson().fromJson(json, type);
            if (parsed != null) {
                values.putAll(parsed);
            }
        } catch (Exception e) {
            String cleaned = json.replace("{", "").replace("}", "").replace("\"", "");
            for (String part : cleaned.split(",")) {
                String[] pair = part.split(":", 2);
                if (pair.length == 2) {
                    values.put(pair[0].trim(), pair[1].trim());
                }
            }
        }
        return values;
    }
}
