package au.edu.safecampus.connect;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/* JADX INFO: loaded from: classes.dex */
public class ConnectionPolicy {
    public void applyConnectionPolicy(HttpsURLConnection connection) throws Exception {
        connection.setSSLSocketFactory(prepareTransport());
        connection.setHostnameVerifier(selectVerifier());
    }

    private SSLSocketFactory prepareTransport() throws Exception {
        TrustManager[] managers = {new X509TrustManager() { // from class: au.edu.safecampus.connect.ConnectionPolicy.1
            @Override // javax.net.ssl.X509TrustManager
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override // javax.net.ssl.X509TrustManager
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            @Override // javax.net.ssl.X509TrustManager
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, managers, new SecureRandom());
        return context.getSocketFactory();
    }

    static /* synthetic */ boolean lambda$selectVerifier$0(String name, SSLSession session) {
        return true;
    }

    private HostnameVerifier selectVerifier() {
        return new HostnameVerifier() { // from class: au.edu.safecampus.connect.ConnectionPolicy$$ExternalSyntheticLambda0
            @Override // javax.net.ssl.HostnameVerifier
            public final boolean verify(String str, SSLSession sSLSession) {
                return ConnectionPolicy.lambda$selectVerifier$0(str, sSLSession);
            }
        };
    }
}
