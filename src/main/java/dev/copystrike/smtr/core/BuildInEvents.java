package dev.copystrike.smtr.core;

import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import dev.copystrike.smtr.core.email.Email;
import dev.copystrike.smtr.core.email.EmailEvents;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Nick on 21/12/20 16:51.
 */
public class BuildInEvents implements EmailEvents {

    @Override
    public void preCycleLoop(HashMap<String, Email> emails) {

    }

    @Override
    public void onOverdue(Email email) {
        ModifyMessageRequest messageRequest = new ModifyMessageRequest().setRemoveLabelIds(Collections.singletonList("UNREAD"));
        try {
            Message readEmail = Sl2To.service.users().messages().modify("me", email.getEmailId(), messageRequest).execute();
            System.out.printf("Email %s has been successfully marked as read.\n", readEmail.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNewAssignment(HashMap<String, Email> emails) {

    }
}
