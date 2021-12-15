package ru.javawebinar.topjava.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class WebUtil {

    public static ResponseEntity<String> getBindingResponse(BindingResult result) {
        String errorFieldsMsg = result.getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("<br>"));
        return new ResponseEntity<>(errorFieldsMsg, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
