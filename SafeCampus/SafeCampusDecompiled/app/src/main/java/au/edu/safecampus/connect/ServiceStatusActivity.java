package au.edu.safecampus.connect;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class ServiceStatusActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper.Screen screen = UiHelper.baseScreen(this, getString(R.string.service_status_title));
        screen.content.addView(UiHelper.body(this, getString(R.string.service_status_body)));
        screen.content.addView(UiHelper.small(this, "Queue status: green\nService window: mock helpdesk desk 4\nDrill note: fictional badge audit at 16:00"));
        setContentView(screen.root);
    }
}
