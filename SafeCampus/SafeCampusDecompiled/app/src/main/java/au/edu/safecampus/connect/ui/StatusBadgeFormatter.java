package au.edu.safecampus.connect.ui;

import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class StatusBadgeFormatter {
    public String badge(String status) {
        return "[" + status.toUpperCase(Locale.US) + "]";
    }
}
