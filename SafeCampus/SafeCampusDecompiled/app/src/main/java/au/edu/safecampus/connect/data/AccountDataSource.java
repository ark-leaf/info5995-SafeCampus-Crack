package au.edu.safecampus.connect.data;

import au.edu.safecampus.connect.workflow.AccountReference;

/* JADX INFO: loaded from: classes.dex */
public class AccountDataSource {
    public AccountReference createReference(String studentId) {
        String value = studentId == null ? "" : studentId.trim();
        return new AccountReference(value, System.currentTimeMillis());
    }
}
