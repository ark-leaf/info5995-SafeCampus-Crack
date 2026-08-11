package au.edu.safecampus.connect.utils;

/* JADX INFO: loaded from: classes.dex */
public class Result<T> {
    public final ErrorResponse error;
    public final T value;

    private Result(T value, ErrorResponse error) {
        this.value = value;
        this.error = error;
    }

    public static <T> Result<T> ok(T value) {
        return new Result<>(value, null);
    }

    public static <T> Result<T> fail(String code, String message) {
        return new Result<>(null, new ErrorResponse(code, message));
    }

    public boolean isOk() {
        return this.error == null;
    }
}
