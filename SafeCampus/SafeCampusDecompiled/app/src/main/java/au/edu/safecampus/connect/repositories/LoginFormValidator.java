package au.edu.safecampus.connect.repositories;

import au.edu.safecampus.connect.utils.ValidationResult;

/* JADX INFO: loaded from: classes.dex */
public class LoginFormValidator {
    public ValidationResult validate(String username, String password) {
        boolean valid = username != null && username.trim().length() > 0 && password != null && password.length() >= 8;
        return new ValidationResult(valid, valid ? "" : "Demo username and password are required");
    }
}
