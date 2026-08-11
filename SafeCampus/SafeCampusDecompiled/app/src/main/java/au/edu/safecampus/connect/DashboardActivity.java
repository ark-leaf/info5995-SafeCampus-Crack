package au.edu.safecampus.connect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class DashboardActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper.Screen screen = UiHelper.baseScreen(this, "Dashboard");
        StudentProfile profile = new StudentProfileRepository().loadDemoProfile();
        TextView student = UiHelper.body(this, profile.displayName);
        TextView studentId = UiHelper.small(this, "Student ID: " + profile.studentId + "\nProgram: " + profile.programName);
        Button profileButton = UiHelper.button(this, "Student Profile");
        Button alerts = UiHelper.button(this, "Campus Alerts");
        Button support = UiHelper.button(this, "Support Request");
        Button token = UiHelper.button(this, "Sync Campus Access");
        Button recovery = UiHelper.button(this, "Account Recovery");
        Button settings = UiHelper.button(this, "Settings");
        Button about = UiHelper.button(this, "About App");
        screen.content.addView(student);
        screen.content.addView(studentId);
        screen.content.addView(profileButton);
        screen.content.addView(alerts);
        screen.content.addView(support);
        screen.content.addView(token);
        screen.content.addView(recovery);
        screen.content.addView(settings);
        screen.content.addView(about);
        setContentView(screen.root);
        profileButton.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.DashboardActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0(view);
            }
        });
        alerts.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.DashboardActivity$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$1(view);
            }
        });
        support.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.DashboardActivity$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$2(view);
            }
        });
        token.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.DashboardActivity$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$3(view);
            }
        });
        recovery.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.DashboardActivity$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$4(view);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.DashboardActivity$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$5(view);
            }
        });
        about.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.DashboardActivity$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$6(view);
            }
        });
        AppStateResolver resolver = new AppStateResolver(this, new CampusConfiguration(this), new SessionState(this));
        if (resolver.shouldOpenPanel(getIntent())) {
            startActivity(new Intent(this, (Class<?>) ServiceStatusActivity.class));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View v) {
        startActivity(new Intent(this, (Class<?>) ProfileActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1(View v) {
        startActivity(new Intent(this, (Class<?>) CampusAlertsActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View v) {
        startActivity(new Intent(this, (Class<?>) SupportRequestActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$3(View v) {
        startActivity(new Intent(this, (Class<?>) SyncAccessTokenActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$4(View v) {
        startActivity(new Intent(this, (Class<?>) AccountRecoveryActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$5(View v) {
        startActivity(new Intent(this, (Class<?>) SettingsActivity.class));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$6(View v) {
        startActivity(new Intent(this, (Class<?>) AboutActivity.class));
    }
}
