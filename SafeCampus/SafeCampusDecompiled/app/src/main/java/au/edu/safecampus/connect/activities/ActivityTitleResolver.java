package au.edu.safecampus.connect.activities;

/* JADX INFO: loaded from: classes.dex */
public class ActivityTitleResolver {
    public String titleFor(String route) {
        if ("alerts".equals(route)) {
            return "Campus Alerts";
        }
        if ("support".equals(route)) {
            return "Support Request";
        }
        if ("sync".equals(route)) {
            return "Sync Campus Access";
        }
        return "SafeCampus Connect";
    }
}
