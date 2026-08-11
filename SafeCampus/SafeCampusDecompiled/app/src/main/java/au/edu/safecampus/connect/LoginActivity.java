package au.edu.safecampus.connect;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class LoginActivity extends Activity {
    private static final String AUTH_DEBUG_ERROR = "Request failed. Debug details: endpoint=/api/v1/auth/login, database=campus_identity, internalServer=10.0.2.15, serviceAccount=auth_api_user, exception=InvalidCredentialException.";
    private static final String DEMO_PASSWORD = "Connect2026!";
    private static final String DEMO_USER = "student";

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper.Screen screen = UiHelper.baseScreen(this, getString(R.string.login_title));
        TextView subtitle = UiHelper.body(this, getString(R.string.login_subtitle));
        final EditText username = UiHelper.input(this, "Username");
        final EditText password = UiHelper.input(this, "Password");
        password.setInputType(129);
        TextView hint = UiHelper.small(this, getString(R.string.demo_hint));
        Button login = UiHelper.button(this, "Sign in");
        final TextView errorDetails = UiHelper.small(this, "");
        errorDetails.setVisibility(8);
        screen.content.addView(subtitle);
        screen.content.addView(username);
        screen.content.addView(password);
        screen.content.addView(hint);
        screen.content.addView(login);
        screen.content.addView(errorDetails);
        setContentView(screen.root);
        login.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.LoginActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0(username, password, errorDetails, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(EditText username, EditText password, TextView errorDetails, View v) {
        String user = username.getText().toString().trim().toLowerCase(Locale.US);
        if (DEMO_USER.equals(user) && DEMO_PASSWORD.equals(password.getText().toString())) {
            SessionState state = new SessionState(this);
            StudentProfile profile = new StudentProfileRepository().loadDemoProfile();
            state.begin(profile);
            new AppPreferences(this).putText("device_label", "Android " + Build.VERSION.RELEASE + " classroom device");
            Intent dashboard = new Intent(this, (Class<?>) DashboardActivity.class);
            if (getIntent() != null && getIntent().hasExtra("profile_bundle")) {
                dashboard.putExtra("profile_bundle", getIntent().getStringExtra("profile_bundle"));
            }
            startActivity(dashboard);
            finish();
            return;
        }
        Toast.makeText(this, "Demo sign-in failed", 0).show();
        errorDetails.setText(AUTH_DEBUG_ERROR);
        errorDetails.setVisibility(0);
    }
}
