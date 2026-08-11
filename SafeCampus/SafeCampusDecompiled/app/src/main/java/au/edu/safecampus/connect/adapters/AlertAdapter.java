package au.edu.safecampus.connect.adapters;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AlertAdapter {
    private final List<String> rows = new ArrayList();

    public void submitRows(List<String> values) {
        this.rows.clear();
        if (values != null) {
            this.rows.addAll(values);
        }
    }

    public String renderPlainText() {
        StringBuilder builder = new StringBuilder();
        for (String row : this.rows) {
            builder.append("- ").append(row).append('\n');
        }
        return builder.toString().trim();
    }
}
