package au.edu.safecampus.connect.session;

/* JADX INFO: loaded from: classes.dex */
public class UserSession {
    private final SessionRecord record;

    public UserSession(SessionRecord record) {
        this.record = record;
    }

    public String displayLine() {
        return this.record.displayName + " (" + this.record.studentId + ")";
    }
}
