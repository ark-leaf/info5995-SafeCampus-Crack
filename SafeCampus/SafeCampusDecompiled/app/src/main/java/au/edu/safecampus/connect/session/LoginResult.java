package au.edu.safecampus.connect.session;

/* JADX INFO: loaded from: classes.dex */
public class LoginResult {
    public final String message;
    public final boolean success;

    public LoginResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
