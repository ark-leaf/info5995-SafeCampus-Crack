package au.edu.safecampus.connect.utils;

/* JADX INFO: loaded from: classes.dex */
public class InputValidator {
    public ValidationResult requireLength(String value, int min) {
        boolean ok = value != null && value.trim().length() >= min;
        return new ValidationResult(ok, ok ? "" : "More detail is required");
    }
}
