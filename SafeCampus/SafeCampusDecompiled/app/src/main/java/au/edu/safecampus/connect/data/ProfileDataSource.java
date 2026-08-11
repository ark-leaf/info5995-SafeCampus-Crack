package au.edu.safecampus.connect.data;

import au.edu.safecampus.connect.models.CourseSummary;
import au.edu.safecampus.connect.models.EnrolmentRecord;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ProfileDataSource {
    public List<EnrolmentRecord> loadEnrolments() {
        List<EnrolmentRecord> records = new ArrayList<>();
        records.add(new EnrolmentRecord("INFO5995", "Secure Software Practice", "active"));
        records.add(new EnrolmentRecord("INFO5301", "Digital Investigation", "active"));
        return records;
    }

    public CourseSummary summary() {
        return new CourseSummary(2, "Semester 2", "North Quad");
    }
}
