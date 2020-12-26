package dev.copystrike.smtr.core.email;

import java.util.HashMap;

/**
 * Created by Nick on 19/12/20 14:49.
 */
public interface EmailEvents {

    /**
     * Will get called everytime it checks the inbox for emails
     * @param emails the emails it has found based on the cycle
     * // TODO: 21/12/20 Find a solution to make it possible to check what kind of cycle this is.
     */
    void preCycleLoop(HashMap<String, Email> emails);

    /**
     * Will get called everytime it checks the inbox for emails
     * @param email the email that does the first cycle
     * // TODO: 21/12/20 Find a solution to make it possible to check what kind of cycle this is.
     */
    void cycleLoop(Email email);

    /**
     * Will be called if email id is not listed in the database.
     * @param email The email
     */
    void onNewEmail(Email email);

    /**
     * Will be called if email id is listed in the database.
     * @param email The email
     */
    void onOldEmail(Email email);

    /**
     * Will be called if a new assignment has been found that is not overdue nor found in the database.
     * @param email The email
     */
    void onNewAssignment(Email email);

    /**
     * Will be called if a new assignment has been found but is is overdue but is not found in the database.
     * Aka a Teacher that assigned work that had to be done before this bot could even fetch it.
     * Or just a teacher that assigned homework you could've only finished if you have a time machine.
     * @param email The email
     */
    void onNewAssignmentButOverDue(Email email);
}
