package ru.javawebinar.topjava.util.converter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    public LocalDateFormatter() {
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public LocalDate parse(String s, Locale locale) {
        return LocalDate.parse(s);
    }
}