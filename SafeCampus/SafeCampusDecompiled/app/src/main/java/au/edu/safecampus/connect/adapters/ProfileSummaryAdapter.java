package au.edu.safecampus.connect.adapters;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ProfileSummaryAdapter {
    public Map<String, String> toRows(String name, String program, String campus) {
        Map<String, String> rows = new LinkedHashMap<>();
        rows.put("Name", name);
        rows.put("Program", program);
        rows.put("Campus", campus);
        return rows;
    }
}
