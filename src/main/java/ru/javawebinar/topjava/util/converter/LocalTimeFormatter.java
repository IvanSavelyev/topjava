package ru.javawebinar.topjava.util.converter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    private DateTimeFormatter formatter;
    private String datePattern;

    public LocalTimeFormatter(String datePattern) {
        this.datePattern = datePattern;
        formatter = DateTimeFormatter.ofPattern(datePattern);
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        return time.format(formatter);
    }

    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        try {
            return LocalTime.parse(s, DateTimeFormatter.ofPattern(datePattern));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("invalid date format. Please use this pattern\"" + datePattern + "\"");
        }
    }
}
