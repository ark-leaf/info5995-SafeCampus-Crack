package au.edu.safecampus.connect;

import android.content.Context;
import android.content.SharedPreferences;

/* JADX INFO: loaded from: classes.dex */
public class AppPreferences {
    private static final String STORE_NAME = "campus_session_cache";
    private final SharedPreferences preferences;

    public AppPreferences(Context context) {
        this.preferences = context.getApplicationContext().getSharedPreferences(STORE_NAME, 0);
    }

    public void putText(String key, String value) {
        this.preferences.edit().putString(key, value).apply();
    }

    public String getText(String key, String fallback) {
        return this.preferences.getString(key, fallback);
    }

    public void putFlag(String key, boolean value) {
        this.preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getFlag(String key, boolean fallback) {
        return this.preferences.getBoolean(key, fallback);
    }
}
