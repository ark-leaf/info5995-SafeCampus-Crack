package au.edu.safecampus.connect;

/* JADX INFO: loaded from: classes.dex */
public class SupportTicketRepository {
    private final ApiClient apiClient;

    public SupportTicketRepository(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public SupportTicket submit(String issue) {
        try {
            String response = this.apiClient.submitSupportRequest(issue);
            if (response != null && response.length() > 0) {
                return new SupportTicket("SC-" + Math.abs(response.hashCode() % 90000), issue, "Received by mock API");
            }
        } catch (Exception e) {
        }
        return new SupportTicket("SC-" + Math.abs((issue + System.currentTimeMillis()).hashCode() % 90000), issue, "Mock success: classroom request recorded");
    }
}
