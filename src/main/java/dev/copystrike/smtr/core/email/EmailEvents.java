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
     * @param emails the emails it has found based on the cycle
     * // TODO: 21/12/20 Find a solution to make it possible to check what kind of cycle this is.
     */
    void cycleLoop(HashMap<String, Email> emails);

    /**
     * Will be called when a assignment deadline is overdue
     * @param emails The emails that are overdue
     */
    void onOverdue(Email emails);

    /**
     * Will be called if there is a new assignment.
     * @param emails The emails that are new
     */
    void onNewAssignment(HashMap<String, Email> emails);
}
