package au.edu.safecampus.connect;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class SessionState {
    private final AppPreferences preferences;

    public SessionState(Context context) {
        this.preferences = new AppPreferences(context);
    }

    public void begin(StudentProfile profile) {
        this.preferences.putText("profile_name", profile.displayName);
        this.preferences.putText("profile_ref", profile.studentId);
        this.preferences.putText("view_anchor", profile.studentId.substring(profile.studentId.length() - 2));
    }

    public void updateDeviceSync(DeviceRecord record) {
        this.preferences.putText("device_sync_state", record.displayValue);
        this.preferences.putText("device_sync_label", record.deviceLabel);
    }

    public String getDeviceSyncState() {
        return this.preferences.getText("device_sync_state", "");
    }

    public String getViewAnchor() {
        return this.preferences.getText("view_anchor", "");
    }

    public boolean hasPanelState() {
        return this.preferences.getFlag("workspace_state", false);
    }

    public void rememberPanelState() {
        this.preferences.putFlag("workspace_state", true);
    }
}
