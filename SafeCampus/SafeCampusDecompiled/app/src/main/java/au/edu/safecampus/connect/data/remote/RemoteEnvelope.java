package au.edu.safecampus.connect.data.remote;

/* JADX INFO: loaded from: classes.dex */
public class RemoteEnvelope {
    public final String body;
    public final int statusCode;

    public RemoteEnvelope(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public boolean isSuccessful() {
        return this.statusCode >= 200 && this.statusCode < 300;
    }
}
