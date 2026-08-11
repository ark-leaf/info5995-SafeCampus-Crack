package au.edu.safecampus.connect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class SyncAccessTokenActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper.Screen screen = UiHelper.baseScreen(this, getString(R.string.token_title));
        final CampusSyncService syncService = new CampusSyncService(this, new ApiClient());
        DeviceRecord record = syncService.syncAccessState();
        final TextView tokenView = UiHelper.body(this, record.displayValue);
        TextView detail = UiHelper.small(this, "Fictional campus access state for classroom use.\nUpdated: " + new DateFormatter().formatShort(record.updatedAt));
        Button refresh = UiHelper.button(this, "Refresh Token");
        screen.content.addView(tokenView);
        screen.content.addView(detail);
        screen.content.addView(refresh);
        setContentView(screen.root);
        refresh.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.SyncAccessTokenActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                tokenView.setText(syncService.syncAccessState().displayValue);
            }
        });
    }
}
