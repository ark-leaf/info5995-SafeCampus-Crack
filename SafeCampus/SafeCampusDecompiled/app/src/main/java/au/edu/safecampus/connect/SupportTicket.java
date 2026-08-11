package au.edu.safecampus.connect;

/* JADX INFO: loaded from: classes.dex */
public class SupportTicket {
    public final String issue;
    public final String reference;
    public final String status;

    public SupportTicket(String reference, String issue, String status) {
        this.reference = reference;
        this.issue = issue;
        this.status = status;
    }
}
