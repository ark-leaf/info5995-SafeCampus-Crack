package au.edu.safecampus.connect;

import android.app.Activity;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class ProfileActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StudentProfile profile = new StudentProfileRepository().loadDemoProfile();
        UiHelper.Screen screen = UiHelper.baseScreen(this, getString(R.string.profile_title));
        screen.content.addView(UiHelper.body(this, profile.displayName));
        screen.content.addView(UiHelper.small(this, "Student ID: " + profile.studentId));
        screen.content.addView(UiHelper.small(this, "Program: " + profile.programName));
        screen.content.addView(UiHelper.small(this, "Campus: " + profile.campus));
        screen.content.addView(UiHelper.small(this, "Contact: " + profile.contactAddress));
        setContentView(screen.root);
    }
}
