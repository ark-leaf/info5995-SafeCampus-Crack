package au.edu.safecampus.connect.services;

/* JADX INFO: loaded from: classes.dex */
public class SyncRequest {
    public final String deviceLabel;
    public final long requestedAt;

    public SyncRequest(String deviceLabel, long requestedAt) {
        this.deviceLabel = deviceLabel;
        this.requestedAt = requestedAt;
    }
}
