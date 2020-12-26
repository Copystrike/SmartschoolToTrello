package dev.copystrike.smtr.core.email;

import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import dev.copystrike.smtr.core.Sl2To;
import dev.copystrike.smtr.core.email.enums.AssignmentType;
import dev.copystrike.smtr.core.email.enums.Profession;
import dev.copystrike.smtr.utils.DateUtil;
import dev.copystrike.smtr.utils.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Nick on 16/12/20 19:24.
 */
public class EmailHelper {

    /**
     * This will get the text from the elements and split the colon once and stores it in a hashmap.<br/>
     * Method: key:value<br/>
     * Example: "ILike: Hey I like: cheese, books" => [ILike, Hey I like: cheese, books]<br/>
     *
     * @param elements elements that contains text
     * @return hashmap of the colon split.
     */
    public HashMap<String, String> elementsFromEmailBodyToMap(Elements elements) {
        AtomicReference<HashMap<String, String>> emailBody = new AtomicReference<>(new HashMap<>());
        elements.forEach(element -> {
            String[] keyValue = element.text().split(":", 2);
            emailBody.get().put(keyValue[0].toLowerCase(), keyValue[1].trim());
        });

        return emailBody.get();
    }

    /**
     * turns the hashmap into an Email object
     *
     * @param emailBodyHashmap hashmap that might have keys like "vak", "type", "omschrijving", etc.<br/>
     *                         Check out {@link EmailHelper#elementsFromEmailBodyToMap(Elements)} could be very interesting for this method.
     * @return A pair of which the key the ID of the email is and the value the email object.
     */
    public Pair<String, Email> hashmapToEmail(Pair<String, HashMap<String, String>> emailBodyHashmap) {
        Pair<String, Email> emailHashMap;
        AtomicReference<Profession> professionReference = new AtomicReference<>();
        AtomicReference<AssignmentType> assignmentType = new AtomicReference<>();
        AtomicReference<String> description = new AtomicReference<>();
        AtomicReference<LocalDate> deadline = new AtomicReference<>();
        AtomicReference<Pair<LocalDate, Integer>> assignedDate = new AtomicReference<>();

        emailBodyHashmap.getValue().forEach((key, value) -> {
            switch (key.toLowerCase()) {
                case "vak":
                    professionReference.set(Arrays.stream(Profession.values())
                            .filter(profession1 -> Arrays.stream(profession1.getEmailNaming())
                                    .anyMatch(s -> Arrays.stream(profession1.getEmailNaming())
                                            .anyMatch(value::contains)))
                            .findFirst()
                            .orElse(null));
                    break;
                case "type":
                    assignmentType.set(Arrays.stream(AssignmentType.values())
                            .filter(assignmentType1 -> assignmentType1.getEmailNaming()
                                    .equalsIgnoreCase(value))
                            .findFirst()
                            .orElse(null));
                    break;
                case "omschrijving":
                    description.set(value);
                    break;
                case "deadline":
                    deadline.set(DateUtil.parseLocalDate(value));
                    break;
                case "opgegeven op":
                    String[] dateClassHour = value.split(",", 2); // vrijdag 11 december 2020, Lesuur 1
                    String dateString = dateClassHour[0];
                    String classHour = dateClassHour[1];
                    String classHourString = classHour.replaceAll("[^0-9]+", "");
                    int classHourInt = Integer.parseInt(classHourString); // This should normally never throw an exception since the regex above delete every non number.
                    assignedDate.set(new Pair<>(DateUtil.parseLocalDate(dateString), classHourInt));
                    break;
                default:
                    System.out.printf("\"%s\" is unknown and has been ignored\n", key);
            }
        });
        Email email = new Email(emailBodyHashmap.getKey(), professionReference.get(), assignmentType.get(), assignedDate.get(), deadline.get(), description.get(), false);
        emailHashMap = new Pair<>(email.getEmailId(), email);
        return emailHashMap;
    }

    /**
     * Turn email body to JSOUP Elements
     *
     * @param body body of the email.
     * @return elements based on email body.
     */
    public Elements emailBodyToElements(String body) {
        Document document = Jsoup.parse(body);
        return document.select("body > div > table > tbody > tr > td > table > tbody > tr:nth-child(4) > td > table > tbody > tr:nth-child(2) > td:nth-child(2) > ul > *");
    }

    /**
     * @param query {@link com.google.api.services.gmail.Gmail.Users.Messages.List#setQ(String)}
     * @return A hashmap of which the key the ID of the email is and the value the email object.
     */
    public HashMap<String, Email> fetchAssignmentEmails(String query) {
        HashMap<String, Email> emails = new HashMap<>();
        try {
            ListMessagesResponse watchResponse = Sl2To.service.users().messages().list("me").setQ(query).execute();
            List<Message> messages = watchResponse.getMessages();
            if (messages == null) return emails;
            for (Message uninitializedMessage : messages) {
                Message message = Sl2To.service.users().messages().get("me", uninitializedMessage.getId()).execute();
                byte[] bodyBytes = message.getPayload().getBody().decodeData();
                Elements emailBodyElements = emailBodyToElements(new String(bodyBytes)); // Turns the html to elements
                HashMap<String, String> emailBodyHashmap = elementsFromEmailBodyToMap(emailBodyElements); // Turns the elements into a hashmap
                Pair<String, Email> emailPair = hashmapToEmail(new Pair<>(message.getId(), emailBodyHashmap)); // Turns hashmap into email object
                emails.put(emailPair.getKey(), emailPair.getValue());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return emails;
    }

    /**
     * @param unReadOnly only fetch mails that are marked unread
     * @return A hashmap of which the key the ID of the email is and the value the email object.
     */
    public HashMap<String, Email> fetchAssignmentEmails(boolean unReadOnly) {
        return fetchAssignmentEmails("label:smartschool \"Type: Taak\" OR \"Type: toets\" " + (unReadOnly ? "is:unread" : ""));
    }

    /**
     * Fetches all emails based on the assignment type.
     *
     * @param assignmentType Type of mails you want
     * @param unReadOnly     Only fetch mails that are marked unread
     * @return A hashmap of which the key the ID of the email is and the value the email object.
     */
    public HashMap<String, Email> fetchAssignmentEmails(AssignmentType assignmentType, boolean unReadOnly) {
        if (assignmentType == AssignmentType.HOMEWORK)
            return fetchAssignmentEmails("label:smartschool Type: Taak " + (unReadOnly ? "is:unread" : ""));
        else if (assignmentType == AssignmentType.TEST)
            return fetchAssignmentEmails("label:smartschool Type: Toets " + (unReadOnly ? "is:unread" : ""));
        else return null; // TODO: 19/12/20 Exception because assignment isn't supported or add query field to enums;
    }
}
