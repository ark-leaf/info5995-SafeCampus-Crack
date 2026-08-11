package au.edu.safecampus.connect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class AccountRecoveryActivity extends Activity {
    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper.Screen screen = UiHelper.baseScreen(this, getString(R.string.recovery_title));
        final EditText studentId = UiHelper.input(this, "Student ID");
        Button generate = UiHelper.button(this, "Generate Recovery Code");
        final TextView code = UiHelper.body(this, "Recovery code will appear here.");
        screen.content.addView(studentId);
        screen.content.addView(generate);
        screen.content.addView(code);
        setContentView(screen.root);
        generate.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.AccountRecoveryActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AccountRecoveryActivity.lambda$onCreate$0(code, studentId, view);
            }
        });
    }

    static /* synthetic */ void lambda$onCreate$0(TextView code, EditText studentId, View v) {
        AccountWorkflow workflow = new AccountWorkflow(new ReferenceCalculator());
        code.setText("Recovery code: " + workflow.createVerificationReference(studentId.getText().toString()));
    }
}
