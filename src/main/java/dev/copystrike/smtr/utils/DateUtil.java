package dev.copystrike.smtr.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Nick on 19/12/20 13:16.
 */
public class DateUtil {

    /**
     * @param dateString dutch date string in pattern "EEEE d MMMM yyyy" example: vrijdag 11 december 2020
     * @return parsed localDate of parameter dateString
     */
    public static LocalDate parseLocalDate(String dateString) {
        Locale dutch = Locale.forLanguageTag("nl-nl");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE d MMMM yyyy").withLocale(dutch);
        return LocalDate.parse(dateString, dateTimeFormatter);
    }
}