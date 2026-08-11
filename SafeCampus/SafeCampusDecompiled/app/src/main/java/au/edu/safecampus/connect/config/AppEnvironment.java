package au.edu.safecampus.connect.config;

/* JADX INFO: loaded from: classes.dex */
public class AppEnvironment {
    private final String endpoint;
    private final String name;

    public AppEnvironment(String name, String endpoint) {
        this.name = name;
        this.endpoint = endpoint;
    }

    public String getName() {
        return this.name;
    }

    public String getEndpoint() {
        return this.endpoint;
    }
}
