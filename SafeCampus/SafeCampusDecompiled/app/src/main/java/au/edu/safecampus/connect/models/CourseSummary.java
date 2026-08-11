package au.edu.safecampus.connect.models;

/* JADX INFO: loaded from: classes.dex */
public class CourseSummary {
    public final int activeCourses;
    public final String campus;
    public final String term;

    public CourseSummary(int activeCourses, String term, String campus) {
        this.activeCourses = activeCourses;
        this.term = term;
        this.campus = campus;
    }
}
