package au.edu.safecampus.connect.repositories;

import au.edu.safecampus.connect.session.LoginResult;

/* JADX INFO: loaded from: classes.dex */
public class AuthenticationRepository {
    public LoginResult authenticate(String username, String password) {
        boolean success = "student".equals(username) && "Connect2026!".equals(password);
        return new LoginResult(success, success ? "Demo session ready" : "Demo sign-in failed");
    }
}
