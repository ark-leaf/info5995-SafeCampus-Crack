package au.edu.safecampus.connect.repositories;

import au.edu.safecampus.connect.utils.ValidationResult;

/* JADX INFO: loaded from: classes.dex */
public class SupportFormValidator {
    public ValidationResult validateIssue(String issue) {
        boolean valid = issue != null && issue.trim().length() >= 4;
        return new ValidationResult(valid, valid ? "" : "Add a short fictional issue summary");
    }
}
