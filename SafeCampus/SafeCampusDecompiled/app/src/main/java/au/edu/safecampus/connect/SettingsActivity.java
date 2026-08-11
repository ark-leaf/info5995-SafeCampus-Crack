package au.edu.safecampus.connect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import au.edu.safecampus.connect.data.local.PreferenceKeys;

/* JADX INFO: loaded from: classes.dex */
public class SettingsActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AppPreferences preferences = new AppPreferences(this);
        UiHelper.Screen screen = UiHelper.baseScreen(this, getString(R.string.settings_title));
        final TextView state = UiHelper.body(this, status(preferences.getFlag(PreferenceKeys.NOTIFY_ALERTS, true)));
        Button toggle = UiHelper.button(this, "Toggle alert notifications");
        screen.content.addView(state);
        screen.content.addView(toggle);
        setContentView(screen.root);
        toggle.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.SettingsActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0(preferences, state, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(AppPreferences preferences, TextView state, View v) {
        boolean next = true ^ preferences.getFlag(PreferenceKeys.NOTIFY_ALERTS, true);
        preferences.putFlag(PreferenceKeys.NOTIFY_ALERTS, next);
        state.setText(status(next));
    }

    private String status(boolean enabled) {
        return enabled ? "Campus alert notifications enabled" : "Campus alert notifications paused";
    }
}
