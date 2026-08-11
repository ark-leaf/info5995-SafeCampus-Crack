package au.edu.safecampus.connect;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class AboutActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper.Screen screen = UiHelper.baseScreen(this, getString(R.string.about_title));
        screen.content.addView(UiHelper.body(this, getString(R.string.about_body)));
        screen.content.addView(UiHelper.small(this, "Build channel: classroom-2026. Public metrics id: pub_sc_demo_84F2A9."));
        setContentView(screen.root);
    }
}
