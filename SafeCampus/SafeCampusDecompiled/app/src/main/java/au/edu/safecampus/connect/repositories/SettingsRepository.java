package au.edu.safecampus.connect.repositories;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class SettingsRepository {
    public Map<String, String> defaultSettings() {
        Map<String, String> settings = new LinkedHashMap<>();
        settings.put("theme", "system");
        settings.put("alerts", "enabled");
        settings.put("dashboard", "compact");
        return settings;
    }
}
