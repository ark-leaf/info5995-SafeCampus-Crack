package au.edu.safecampus.connect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent next = new Intent(this, (Class<?>) LoginActivity.class);
        if (getIntent() != null && getIntent().hasExtra("profile_bundle")) {
            next.putExtra("profile_bundle", getIntent().getStringExtra("profile_bundle"));
        }
        startActivity(next);
        finish();
    }
}
