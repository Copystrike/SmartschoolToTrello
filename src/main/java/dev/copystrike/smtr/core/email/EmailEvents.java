package dev.copystrike.smtr.core.email;

/**
 * Created by Nick on 19/12/20 14:49.
 */
public interface EmailEvents {

    /**
     * Will be called when it fetches emails
     *
     * @param newEmails the new emails it has fetched
     */
    void onEmailFetch(Email[] newEmails);
}
