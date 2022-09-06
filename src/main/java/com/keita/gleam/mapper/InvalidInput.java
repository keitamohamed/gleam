package com.keita.gleam.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class InvalidInput {

    public InvalidInput () {}

    public static ResponseEntity<Object> error (BindingResult bindingResult, HttpStatus status) {
        Map<String, Object> message = new HashMap<>();
        Map<String, String> errorMap = new HashMap<>();

        message.put("status", status.name());
        message.put("code", String.valueOf(status.value()));
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        message.put("error", errorMap);
        return new ResponseEntity<>(message, status);
    }

}
