package au.edu.safecampus.connect;

/* JADX INFO: loaded from: classes.dex */
public class CampusAlert {
    public final String message;
    public final String title;
    public final long updatedAt;

    public CampusAlert(String title, String message, long updatedAt) {
        this.title = title;
        this.message = message;
        this.updatedAt = updatedAt;
    }
}
