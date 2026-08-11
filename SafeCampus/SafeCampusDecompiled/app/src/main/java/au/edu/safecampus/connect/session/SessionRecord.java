package au.edu.safecampus.connect.session;

/* JADX INFO: loaded from: classes.dex */
public class SessionRecord {
    public final String displayName;
    public final long startedAt;
    public final String studentId;

    public SessionRecord(String displayName, String studentId, long startedAt) {
        this.displayName = displayName;
        this.studentId = studentId;
        this.startedAt = startedAt;
    }
}
