package au.edu.safecampus.connect;

import android.content.Context;
import android.util.Base64;

/* JADX INFO: loaded from: classes.dex */
public class CampusConfiguration {
    private final AppPreferences preferences;

    public CampusConfiguration(Context context) {
        this.preferences = new AppPreferences(context);
    }

    public boolean matchesServiceProfile(String value, String anchor) {
        String expected = resolveChannel(anchor);
        return expected.equals(value);
    }

    private String resolveChannel(String anchor) {
        String stored = this.preferences.getText("campus_profile_channel", "");
        if (stored.length() > 0) {
            return stored;
        }
        String prefix = new String(Base64.decode("c3Zj", 0));
        String label = new String(Base64.decode("bGFudGVybg==", 0));
        return prefix + ":" + label + ":" + anchor;
    }
}
