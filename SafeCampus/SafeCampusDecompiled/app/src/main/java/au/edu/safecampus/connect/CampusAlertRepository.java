package au.edu.safecampus.connect;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CampusAlertRepository {
    private final ApiClient apiClient;

    public CampusAlertRepository(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<CampusAlert> loadAlerts() {
        try {
            Log.d("SafeCampus", "alerts: requesting classroom alert feed");
            String response = this.apiClient.fetchCampusAlerts();
            List<CampusAlert> parsed = new ResponseParser().parseAlerts(response);
            if (!parsed.isEmpty()) {
                return parsed;
            }
        } catch (Exception e) {
            Log.d("SafeCampus", "alerts: using local classroom alert feed");
        }
        return localAlerts();
    }

    private List<CampusAlert> localAlerts() {
        List<CampusAlert> alerts = new ArrayList<>();
        long now = System.currentTimeMillis();
        alerts.add(new CampusAlert("Library entrance update", "West entrance closed for a fictional safety drill.", now - 300000));
        alerts.add(new CampusAlert("Cyber safety workshop", "Room B204 hosts a mock password safety session at 2:00 PM.", now - 900000));
        alerts.add(new CampusAlert("Shuttle route notice", "Route C delayed by simulated roadworks near the sports centre.", now - 1800000));
        return alerts;
    }
}
