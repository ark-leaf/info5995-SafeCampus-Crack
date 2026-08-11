package au.edu.safecampus.connect;

/* JADX INFO: loaded from: classes.dex */
public class StudentProfile {
    public final String campus;
    public final String contactAddress;
    public final String displayName;
    public final String programName;
    public final String studentId;

    public StudentProfile(String displayName, String studentId, String programName, String campus, String contactAddress) {
        this.displayName = displayName;
        this.studentId = studentId;
        this.programName = programName;
        this.campus = campus;
        this.contactAddress = contactAddress;
    }
}
