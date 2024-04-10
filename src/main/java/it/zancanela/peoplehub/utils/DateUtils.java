package it.zancanela.peoplehub.utils;

import it.zancanela.peoplehub.exceptions.PeopleHubException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static synchronized LocalDate stringToLocalDate(String string) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            return LocalDate.parse(string, formatter);
        } catch (Exception e) {
            throw new PeopleHubException("The date entered [" +
                    string +
                    "] cannot be converted");
        }

    }

}
