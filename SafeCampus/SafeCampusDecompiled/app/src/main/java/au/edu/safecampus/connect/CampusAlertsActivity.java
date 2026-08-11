package au.edu.safecampus.connect;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class CampusAlertsActivity extends Activity {
    private TextView alertsText;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper.Screen screen = UiHelper.baseScreen(this, getString(R.string.alerts_title));
        this.alertsText = UiHelper.body(this, "Retrieving alerts...");
        screen.content.addView(this.alertsText);
        setContentView(screen.root);
        loadAlerts();
    }

    private void loadAlerts() {
        new Thread(new Runnable() { // from class: au.edu.safecampus.connect.CampusAlertsActivity$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadAlerts$1();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadAlerts$1() {
        String message;
        CampusAlertRepository repository = new CampusAlertRepository(new ApiClient());
        try {
            StringBuilder builder = new StringBuilder();
            for (CampusAlert alert : repository.loadAlerts()) {
                builder.append(alert.title).append("\n").append(new DateFormatter().formatShort(alert.updatedAt)).append(" - ").append(alert.message).append("\n\n");
            }
            message = builder.toString().trim();
        } catch (Exception e) {
            message = "Local mock alerts unavailable.";
        }
        final String result = message;
        runOnUiThread(new Runnable() { // from class: au.edu.safecampus.connect.CampusAlertsActivity$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$loadAlerts$0(result);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$loadAlerts$0(String result) {
        this.alertsText.setText(result);
    }
}
