package au.edu.safecampus.connect.adapters;

/* JADX INFO: loaded from: classes.dex */
public class SettingsRowAdapter {
    public String describeToggle(String label, boolean enabled) {
        return label + ": " + (enabled ? "enabled" : "paused");
    }
}
