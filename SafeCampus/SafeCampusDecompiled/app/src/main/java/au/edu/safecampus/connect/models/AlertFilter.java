package au.edu.safecampus.connect.models;

/* JADX INFO: loaded from: classes.dex */
public class AlertFilter {
    private final AlertCategory category;

    public AlertFilter(AlertCategory category) {
        this.category = category;
    }

    public boolean accepts(AlertCategory value) {
        return this.category == null || this.category == value;
    }
}
