package com.followup;

import java.io.InputStream;
import java.util.Properties;

/**
 * Handles sending follow-up reminder emails.
 * Phase 1: Console + log based (no real email).
 * Phase 2: SMTP / Gmail / SES.
 */
public class EmailService {

    private final String fromEmail;

    public EmailService() {
        this.fromEmail = loadFromEmail();
    }

    /**
     * Send reminder email for missed follow-up
     */
    public void sendReminderEmail(ClientFollowUp followUp) {

        // Phase 1: Just log (simulate sending email)
        System.out.println("📧 Sending reminder email...");
        System.out.println("From   : " + fromEmail);
        System.out.println("To     : " + followUp.getClientEmail());
        System.out.println("Subject: Follow-up reminder");
        System.out.println("Body   : You missed a follow-up with "
                + followUp.getClientName()
                + " scheduled on "
                + followUp.getFollowUpDate());
        System.out.println("✅ Reminder email sent (simulated)");
    }

    /**
     * Load email config from config.properties
     */
    private String loadFromEmail() {
        Properties properties = new Properties();

        try (InputStream input =
                     getClass().getClassLoader().getResourceAsStream("config.properties")) {

            if (input != null) {
                properties.load(input);
                return properties.getProperty("email.from", "noreply@followup.local");
            }

        } catch (Exception e) {
            System.err.println("⚠️ Failed to load email config, using default");
        }

        return "noreply@followup.local";
    }
}
