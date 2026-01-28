package com.followup;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Scheduler checks missed follow-ups at a fixed interval.
 * If follow-up date is passed and status is still PENDING,
 * it triggers an email reminder.
 */
public class Scheduler {

    private final Storage storage;
    private final EmailService emailService;
    private final ScheduledExecutorService executor;

    // Check interval (in minutes)
    private static final long CHECK_INTERVAL_MINUTES = 60;

    public Scheduler(Storage storage, EmailService emailService) {
        this.storage = storage;
        this.emailService = emailService;
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Starts the scheduler
     */
    public void start() {
        System.out.println("📅 Scheduler started. Checking every "
                + CHECK_INTERVAL_MINUTES + " minutes.");

        executor.scheduleAtFixedRate(
                this::checkMissedFollowUps,
                0,
                CHECK_INTERVAL_MINUTES,
                TimeUnit.MINUTES
        );
    }

    /**
     * Core logic:
     * - Get all pending follow-ups
     * - If follow-up date < today → send email
     */
    private void checkMissedFollowUps() {
        try {
            System.out.println("🔍 Checking missed follow-ups...");

            List<ClientFollowUp> pendingFollowUps = storage.getPendingFollowUps();
            LocalDate today = LocalDate.now();

            for (ClientFollowUp followUp : pendingFollowUps) {

                if (followUp.getFollowUpDate().isBefore(today)) {

                    System.out.println("⚠️ Missed follow-up detected for "
                            + followUp.getClientEmail());

                    emailService.sendReminderEmail(followUp);

                    storage.markReminderSent(followUp.getId());
                }
            }

        } catch (Exception e) {
            System.err.println("❌ Error while checking follow-ups");
            e.printStackTrace();
        }
    }

    /**
     * Graceful shutdown (optional)
     */
    public void stop() {
        executor.shutdown();
        System.out.println("🛑 Scheduler stopped.");
    }
}
