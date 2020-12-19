package dev.copystrike.smtr.core.email;

import dev.copystrike.smtr.core.email.enums.AssignmentType;
import dev.copystrike.smtr.core.email.enums.Profession;
import dev.copystrike.smtr.utils.EmailUtil;
import javafx.util.Pair;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
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
     * @return Email object based of the emailBodyHashmap parameter.
     */
    public Email hashmapToEmail(HashMap<String, String> emailBodyHashmap) {
        AtomicReference<Profession> professionReference = new AtomicReference<>();
        AtomicReference<AssignmentType> assignmentType = new AtomicReference<>();
        AtomicReference<String> description = new AtomicReference<>();
        AtomicReference<LocalDate> deadline = new AtomicReference<>();
        AtomicReference<Pair<LocalDate, Integer>> assignedDate = new AtomicReference<>();

        emailBodyHashmap.forEach((key, value) -> {
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
                    deadline.set(EmailUtil.parseLocalDate(value));
                    break;
                case "opgegeven op":
                    String[] dateClassHour = value.split(",", 2); // vrijdag 11 december 2020, Lesuur 1
                    String dateString = dateClassHour[0];
                    String classHour = dateClassHour[1];
                    String classHourString = classHour.replaceAll("[^0-9]+", "");
                    int classHourInt = Integer.parseInt(classHourString); // This should normally never throw an exception since the regex above delete every non number.
                    assignedDate.set(new Pair<>(EmailUtil.parseLocalDate(dateString), classHourInt));
                    break;
                default:
                    System.out.printf("\"%s\" is unknown and has been ignored\n", key);
            }
        });
        return new Email(professionReference.get(), assignmentType.get(), assignedDate.get(), deadline.get(), description.get());
    }
}
