package dev.copystrike.smtr.core.email;

import dev.copystrike.smtr.core.email.enums.AssignmentType;
import dev.copystrike.smtr.core.email.enums.Profession;
import javafx.util.Pair;

import java.time.LocalDate;

/**
 * Created by Nick on 16/12/20 16:48.
 */
public class Email {

    private final String emailId;
    private Profession profession;
    private AssignmentType assignmentType;
    private Pair<LocalDate, Integer> assignedDate;
    private LocalDate deadline;
    private String description;
    private String raw;
    private boolean alreadyChecked;

    public Email(String emailId, Profession profession, AssignmentType assignmentType, Pair<LocalDate, Integer> assignedDate, LocalDate deadline, String description, boolean alreadyChecked) {
        this.emailId = emailId;
        this.profession = profession;
        this.assignmentType = assignmentType;
        this.deadline = deadline;
        this.assignedDate = assignedDate;
        this.description = description;
        this.alreadyChecked = alreadyChecked;
    }

    public String getEmailId() {
        return emailId;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public AssignmentType getReminderType() {
        return assignmentType;
    }

    public void setReminderType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public AssignmentType getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(AssignmentType assignmentType) {
        this.assignmentType = assignmentType;
    }

    public Pair<LocalDate, Integer> getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Pair<LocalDate, Integer> assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public boolean isAlreadyChecked() {
        return alreadyChecked;
    }

    public void setAlreadyChecked(boolean alreadyChecked) {
        this.alreadyChecked = alreadyChecked;
    }
}
