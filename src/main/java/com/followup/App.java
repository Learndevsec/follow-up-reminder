package com.followup;

public class App {

    public static void main(String[] args) {

        System.out.println("🚀 Follow-up Reminder Application Started");

        try {
            // Initialize storage (JSON for now)
            Storage storage = new JsonStorage();

            // Initialize email service
            EmailService emailService = new EmailService();

            // Start scheduler
            Scheduler scheduler = new Scheduler(storage, emailService);
            scheduler.start();

            // Keep application running
            System.out.println("⏰ Scheduler is running. Waiting for missed follow-ups...");

        } catch (Exception e) {
            System.err.println("❌ Failed to start application");
            e.printStackTrace();
        }
    }
}
