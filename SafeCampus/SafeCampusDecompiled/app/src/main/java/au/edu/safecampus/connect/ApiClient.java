package au.edu.safecampus.connect;

import java.io.OutputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/* JADX INFO: loaded from: classes.dex */
public class ApiClient {
    private static final String BASE_URL = "https://api.safecampus.invalid/";
    private final ResponseParser responseParser = new ResponseParser();
    private final NetworkRequestFactory requestFactory = new NetworkRequestFactory(BASE_URL);

    public String fetchCampusAlerts() throws Exception {
        HttpsURLConnection connection = createClient("alerts");
        return this.responseParser.readText(connection);
    }

    public String submitSupportRequest(String body) throws Exception {
        HttpsURLConnection connection = createClient("support/request");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        byte[] payload = ("issue=" + body).getBytes("UTF-8");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setFixedLengthStreamingMode(payload.length);
        OutputStream stream = connection.getOutputStream();
        stream.write(payload);
        stream.close();
        return this.responseParser.readText(connection);
    }

    public String fetchSyncEnvelope() throws Exception {
        HttpsURLConnection connection = createClient("access/sync");
        return this.responseParser.readText(connection);
    }

    private HttpsURLConnection createClient(String path) throws Exception {
        URL url = this.requestFactory.buildUrl(path);
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        configureConnection(connection);
        return connection;
    }

    private void configureConnection(HttpsURLConnection connection) throws Exception {
        connection.setConnectTimeout(1500);
        connection.setReadTimeout(1500);
        connection.setRequestProperty("X-Client", "SafeCampusConnect-Android");
        new ConnectionPolicy().applyConnectionPolicy(connection);
    }
}
