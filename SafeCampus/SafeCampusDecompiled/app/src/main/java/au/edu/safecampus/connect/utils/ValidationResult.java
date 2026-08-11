package au.edu.safecampus.connect.utils;

/* JADX INFO: loaded from: classes.dex */
public class ValidationResult {
    public final String message;
    public final boolean valid;

    public ValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }
}
