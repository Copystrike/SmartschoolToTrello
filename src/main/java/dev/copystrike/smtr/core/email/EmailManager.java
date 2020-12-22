package dev.copystrike.smtr.core.email;

import dev.copystrike.smtr.file.EmailConfig;

import java.time.LocalDate;
import java.time.ZoneId;
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

    public void start() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                HashMap<String, Email> emails = emailHelper.fetchEmails(true);
                emailEvents.forEach(emailEvents -> emailEvents.preCycleLoop(emails));

                for (Email email : emails.values()) {
                    emailEvents.forEach(emailEvents -> emailEvents.preCycleLoop(emails));
                    boolean emailAlreadyChecked = emailConfig.getAlreadyCheckedEmails().contains(email.getEmailId());
                    LocalDate localDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (email.getDeadline().isBefore(localDate)) {
                        emailEvents.forEach(emailEvents -> emailEvents.preCycleLoop(emails));
                        continue;
                    }


                    if (emailAlreadyChecked){
                        email.setAlreadyChecked(true);
                        emailConfig.addEmailID(email.getEmailId());
                    }
                }
            }
        }, 0L, TimeUnit.SECONDS.toMillis(50000L));
    }

}
