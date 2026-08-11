package au.edu.safecampus.connect;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class CampusSyncService {
    private final ApiClient apiClient;
    private final Context context;
    private final SessionState sessionState;

    public CampusSyncService(Context context, ApiClient apiClient) {
        this.context = context.getApplicationContext();
        this.apiClient = apiClient;
        this.sessionState = new SessionState(this.context);
    }

    public DeviceRecord syncAccessState() {
        String envelope;
        try {
            envelope = this.apiClient.fetchSyncEnvelope();
        } catch (Exception e) {
            envelope = "SC-CAMPUS-" + Math.abs((System.currentTimeMillis() / 1000) % 1000000);
        }
        AppPreferences preferences = new AppPreferences(this.context);
        String label = preferences.getText("device_label", "Android classroom device");
        DeviceRecord record = new DeviceRecord(label, envelope, System.currentTimeMillis());
        this.sessionState.updateDeviceSync(record);
        return record;
    }
}
