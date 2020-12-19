package dev.copystrike.smtr.core.email.enums;

/**
 * Created by Nick on 16/12/20 16:49.
 */
public enum Profession {
    PNEUMATIC("PNEU"),
    MAVO("MAV", "MAATSCHAPPELIJKE VORMING"),
    CAD("TT"),
    TECH("TECHNOLOGIE ELEKTRICITEIT"),
    ELE("ELEKTRICITEIT"),
    ENGELS("ENGELS"),
    PREL("PREL"),
    NEDERLANDS("NED"),
    ECONOMIE("ECO"),
    PLC("PLC"),
    WISKUNDE("WISKUNDE"),
    ZEDELEER("NCZ6"),
    LO("MAATSCHAPPELIJKE VORMING");

    private final String[] emailNaming;

    Profession(String... classCatchName) {
        this.emailNaming = classCatchName;
    }

    /**
     * @return A list of names used in the mail to refer to a profession.
     */
    public String[] getEmailNaming() {
        return emailNaming;
    }
}
