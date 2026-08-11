package au.edu.safecampus.connect;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class SupportRequestActivity extends Activity {
    private TextView status;

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UiHelper.Screen screen = UiHelper.baseScreen(this, getString(R.string.support_title));
        final EditText issue = UiHelper.input(this, "Fictional issue");
        Button submit = UiHelper.button(this, "Submit Request");
        this.status = UiHelper.small(this, "Requests are sent to the classroom mock API only.");
        screen.content.addView(issue);
        screen.content.addView(submit);
        screen.content.addView(this.status);
        setContentView(screen.root);
        submit.setOnClickListener(new View.OnClickListener() { // from class: au.edu.safecampus.connect.SupportRequestActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.lambda$onCreate$0(issue, view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(EditText issue, View v) {
        submitIssue(issue.getText().toString());
    }

    private void submitIssue(final String issue) {
        if (issue == null || issue.trim().length() < 4) {
            this.status.setText("Please enter a fictional issue before submitting.");
        } else {
            this.status.setText("Submitting...");
            new Thread(new Runnable() { // from class: au.edu.safecampus.connect.SupportRequestActivity$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$submitIssue$2(issue);
                }
            }).start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitIssue$2(String issue) {
        SupportTicketRepository repository = new SupportTicketRepository(new ApiClient());
        SupportTicket ticket = repository.submit(issue);
        final String result = "Reference " + ticket.reference + ": " + ticket.status;
        runOnUiThread(new Runnable() { // from class: au.edu.safecampus.connect.SupportRequestActivity$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$submitIssue$1(result);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$submitIssue$1(String result) {
        this.status.setText(result);
    }
}
