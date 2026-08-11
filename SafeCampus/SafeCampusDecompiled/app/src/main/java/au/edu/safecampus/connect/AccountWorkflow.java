package au.edu.safecampus.connect;

import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class AccountWorkflow {
    private final ReferenceCalculator calculator;

    public AccountWorkflow(ReferenceCalculator calculator) {
        this.calculator = calculator;
    }

    public String createVerificationReference(String accountReference) {
        String normalised = normaliseAccountReference(accountReference);
        int daily = this.calculator.calculateDailyReference(normalised);
        int folded = this.calculator.foldReference(normalised, daily);
        return String.format(Locale.US, "RC-%06d", Integer.valueOf(folded));
    }

    public String normaliseAccountReference(String accountReference) {
        String value = accountReference == null ? "" : accountReference.trim().toUpperCase(Locale.US);
        return value.replace("-", "").replace(" ", "");
    }
}
