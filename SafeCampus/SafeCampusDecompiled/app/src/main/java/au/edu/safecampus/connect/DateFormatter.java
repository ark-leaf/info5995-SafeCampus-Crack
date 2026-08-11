package au.edu.safecampus.connect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class DateFormatter {
    public String formatShort(long timestamp) {
        return new SimpleDateFormat("dd MMM, h:mm a", Locale.US).format(new Date(timestamp));
    }
}
