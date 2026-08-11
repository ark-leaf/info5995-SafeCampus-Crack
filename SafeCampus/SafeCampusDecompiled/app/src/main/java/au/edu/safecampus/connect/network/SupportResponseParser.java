package au.edu.safecampus.connect.network;

/* JADX INFO: loaded from: classes.dex */
public class SupportResponseParser {
    public String referenceFrom(String body, String fallback) {
        if (body == null || body.trim().isEmpty()) {
            return fallback;
        }
        return "SC-" + Math.abs(body.hashCode() % 90000);
    }
}
