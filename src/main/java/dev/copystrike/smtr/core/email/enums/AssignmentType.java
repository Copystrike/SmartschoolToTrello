package dev.copystrike.smtr.core.email.enums;

/**
 * Created by Nick on 16/12/20 19:13.
 */
public enum AssignmentType {

    HOMEWORK("TAAK"),
    TEST("TOETS");

    private final String emailNaming;

    AssignmentType(String emailNaming) {
        this.emailNaming = emailNaming;
    }

    public String getEmailNaming() {
        return emailNaming;
    }
}
