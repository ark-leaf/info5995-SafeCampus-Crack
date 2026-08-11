package au.edu.safecampus.connect.services;

import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class DeviceRegistrationManager {
    public String createLabel() {
        return "Android " + Build.VERSION.RELEASE + " classroom device";
    }
}
