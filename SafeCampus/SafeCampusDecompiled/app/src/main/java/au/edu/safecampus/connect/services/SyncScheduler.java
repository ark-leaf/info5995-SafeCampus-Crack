package au.edu.safecampus.connect.services;

/* JADX INFO: loaded from: classes.dex */
public class SyncScheduler {
    public long nextWindow(long now) {
        return 900000 + now;
    }
}
