package au.edu.safecampus.connect.utils;

/* JADX INFO: loaded from: classes.dex */
public class StringSanitizer {
    public String cleanSingleLine(String value) {
        return value == null ? "" : value.replace('\n', ' ').replace('\r', ' ').trim();
    }
}
