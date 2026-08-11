package au.edu.safecampus.connect.repositories;

import au.edu.safecampus.connect.models.TicketCategory;
import au.edu.safecampus.connect.models.TicketStatus;

/* JADX INFO: loaded from: classes.dex */
public class SupportRequestManager {
    public String composeReference(String issue, TicketCategory category) {
        String seed = (issue == null ? "" : issue) + ":" + category.name();
        return "SC-" + Math.abs(seed.hashCode() % 90000);
    }

    public TicketStatus initialStatus() {
        return TicketStatus.SUBMITTED;
    }
}
