package ru.javawebinar.topjava.util.converter;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    @Override
    public String print(LocalDate date, Locale locale) {
        return date.format(DateTimeFormatter.ISO_DATE);
    }

    @Override
    public LocalDate parse(String s, Locale locale) {
        return LocalDate.parse(s);
    }
}