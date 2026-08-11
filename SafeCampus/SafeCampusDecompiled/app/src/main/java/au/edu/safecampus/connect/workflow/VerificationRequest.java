package au.edu.safecampus.connect.workflow;

/* JADX INFO: loaded from: classes.dex */
public class VerificationRequest {
    public final String channel;
    public final AccountReference reference;

    public VerificationRequest(AccountReference reference, String channel) {
        this.reference = reference;
        this.channel = channel;
    }
}
