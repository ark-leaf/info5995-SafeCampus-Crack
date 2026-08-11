package au.edu.safecampus.connect.workflow;

/* JADX INFO: loaded from: classes.dex */
public class VerificationResult {
    public final String code;
    public final long expiresAt;

    public VerificationResult(String code, long expiresAt) {
        this.code = code;
        this.expiresAt = expiresAt;
    }
}
