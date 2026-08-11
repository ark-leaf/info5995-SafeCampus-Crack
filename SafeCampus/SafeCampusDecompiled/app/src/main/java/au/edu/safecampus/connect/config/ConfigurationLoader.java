package au.edu.safecampus.connect.config;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ConfigurationLoader {
    public Map<String, String> loadDefaults() {
        Map<String, String> values = new LinkedHashMap<>();
        values.put("environment", "classroom");
        values.put("endpoint", "https://api.safecampus.invalid/");
        values.put("metrics", "pub_sc_demo_84F2A9");
        return values;
    }
}
