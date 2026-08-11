package au.edu.safecampus.connect;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class ReferenceCalculator {
    public int calculateDailyReference(String value) {
        String day = new SimpleDateFormat("yyDDD", Locale.US).format(new Date());
        int base = Integer.parseInt(day);
        for (int i = 0; i < value.length(); i++) {
            base = (base * 33) ^ value.charAt(i);
        }
        int i2 = Math.abs(base);
        return i2;
    }

    public int foldReference(String value, int dailyReference) {
        int accountPart = value.length() >= 5 ? value.substring(value.length() - 5).hashCode() : value.hashCode();
        return Math.abs(((accountPart * 17) ^ dailyReference) % 1000000);
    }
}
