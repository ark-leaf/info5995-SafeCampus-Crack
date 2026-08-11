package au.edu.safecampus.connect;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;

/* JADX INFO: loaded from: classes.dex */
public class AppStateResolver {
    private final CampusConfiguration configuration;
    private final AppPreferences preferences;
    private final SessionState sessionState;

    public AppStateResolver(Context context, CampusConfiguration configuration, SessionState sessionState) {
        this.preferences = new AppPreferences(context);
        this.configuration = configuration;
        this.sessionState = sessionState;
    }

    public boolean shouldOpenPanel(Intent intent) {
        if (this.sessionState.hasPanelState()) {
            return true;
        }
        String value = readBundleValue(intent);
        if (!hasLocalContext(value)) {
            return false;
        }
        return bindResolvedValue(value);
    }

    private String readBundleValue(Intent intent) {
        String bundle = intent == null ? "" : intent.getStringExtra("profile_bundle");
        if (bundle == null || bundle.length() < 8) {
            return "";
        }
        return new String(Base64.decode(bundle, 0));
    }

    private boolean hasLocalContext(String value) {
        return value.length() > 0 && this.sessionState.getViewAnchor().length() > 0 && this.preferences.getText("profile_ref", "").length() > 0;
    }

    private boolean bindResolvedValue(String value) {
        boolean resolved = this.configuration.matchesServiceProfile(value, this.sessionState.getViewAnchor());
        if (resolved) {
            this.sessionState.rememberPanelState();
        }
        return resolved;
    }
}
