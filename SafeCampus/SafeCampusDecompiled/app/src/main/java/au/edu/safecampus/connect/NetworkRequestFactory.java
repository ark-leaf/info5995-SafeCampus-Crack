package au.edu.safecampus.connect;

import java.net.URL;

/* JADX INFO: loaded from: classes.dex */
public class NetworkRequestFactory {
    private final String baseUrl;

    public NetworkRequestFactory(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public URL buildUrl(String path) throws Exception {
        String cleanPath = path != null ? path.replaceFirst("^/+", "") : "";
        return new URL(this.baseUrl + cleanPath);
    }
}
