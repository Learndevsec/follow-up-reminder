package com.followup;

import java.util.List;

/**
 * Storage abstraction.
 * Can be implemented using JSON, H2, PostgreSQL, etc.
 */
public interface Storage {

    /**
     * Returns all follow-ups that are still pending.
     */
    List<ClientFollowUp> getPendingFollowUps();

    /**
     * Marks that a reminder email was sent
     * so it is not sent again.
     */
    void markReminderSent(String followUpId);

}
