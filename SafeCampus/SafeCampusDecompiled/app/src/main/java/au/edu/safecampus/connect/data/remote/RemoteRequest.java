package au.edu.safecampus.connect.data.remote;

import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class RemoteRequest {
    public final Map<String, String> headers = new LinkedHashMap();
    public final String path;

    public RemoteRequest(String path) {
        this.path = path;
    }

    public RemoteRequest withHeader(String name, String value) {
        this.headers.put(name, value);
        return this;
    }
}
