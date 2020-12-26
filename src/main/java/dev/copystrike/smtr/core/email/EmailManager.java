package dev.copystrike.smtr.core.email;

import dev.copystrike.smtr.file.EmailConfig;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nick on 19/12/20 15:19.
 */
public class EmailManager {

    private final EmailHelper emailHelper;
    private final EmailConfig emailConfig;
    private final List<EmailEvents> emailEvents;

    public EmailManager() {
        this.emailHelper = new EmailHelper();
        emailConfig = new EmailConfig();
        emailEvents = new ArrayList<>();
    }

    /**
     * This will start a scheduler that will call in the events
     */
    public void start() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                HashMap<String, Email> emails = emailHelper.fetchAssignmentEmails(true);
                emailEvents.forEach(emailEvents -> emailEvents.preCycleLoop(emails));
                for (Email email : emails.values()) {
                    emailEvents.forEach(emailEvents -> emailEvents.cycleLoop(email));
                    boolean emailAlreadyChecked = emailConfig.getAlreadyCheckedEmails().contains(email.getEmailId());
                    if (!emailAlreadyChecked){
                        emailEvents.forEach(emailEvents -> emailEvents.onNewEmail(email));
                        if (email.isOverdue()) {
                            emailEvents.forEach(emailEvents -> emailEvents.onNewAssignmentButOverDue(email));
                            emailEvents.forEach(emailEvents -> emailEvents.onOldEmail(email));
                        } else {
                            emailEvents.forEach(emailEvents -> emailEvents.onNewAssignment(email));
                        }
                        email.setAlreadyChecked(true);
                        emailConfig.addEmailID(email.getEmailId());
                    } else {
                        emailEvents.forEach(emailEvents -> emailEvents.onOldEmail(email));
                    }
                }
            }
        }, 0L, TimeUnit.MINUTES.toMillis(30));
    }

    public List<EmailEvents> getEmailEvents() {
        return emailEvents;
    }
}
