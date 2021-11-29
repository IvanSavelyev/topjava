package ru.javawebinar.topjava.util.converter;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    public LocalTimeFormatter() {
    }

    @Override
    public String print(LocalTime time, Locale locale) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    @Override
    public LocalTime parse(String s, Locale locale) throws ParseException {
        return LocalTime.parse(s);
    }
}
