package au.edu.safecampus.connect.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class AppLogger {
    public void debug(String area, String message) {
        Log.d("SafeCampus", area + ": " + message);
    }
}
