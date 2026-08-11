package au.edu.safecampus.connect.workflow;

/* JADX INFO: loaded from: classes.dex */
public class AccountReference {
    public final long requestedAt;
    public final String studentId;

    public AccountReference(String studentId, long requestedAt) {
        this.studentId = studentId;
        this.requestedAt = requestedAt;
    }
}
