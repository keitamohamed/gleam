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
        Map<String, Object> errorMap = new HashMap<>();
        Map<String, String> addressError = new HashMap<>();

        message.put("status", status.name());
        message.put("code", String.valueOf(status.value()));
        int count = 0;
        int index = 0;
        boolean addressErrors = false;
        for (FieldError error : bindingResult.getFieldErrors()) {
            if (error.getField().contains("address")) {
                String replace = error.getField().trim().replace("address["+ index + "].", "");
                addressError.put(replace, error.getDefaultMessage());
                count += 1;
                addressErrors = true;
            } else {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            if (count == 4) {
                count = 0;
                index++;
            }
        }
        if (addressErrors) {
            errorMap.put("address", addressError);
        }
        message.put("error", errorMap);
        return new ResponseEntity<>(message, status);
    }
//    public static ResponseEntity<Object> errorAddress (BindingResult bindingResult, HttpStatus status) {
//        Map<String, Object> message = new HashMap<>();
//        Map<String, Object> errorMap = new HashMap<>();
//
//        message.put("status", status.name());
//        message.put("code", String.valueOf(status.value()));
//        for (FieldError error : bindingResult.getFieldErrors()) {
//            errorMap.put(error.getField(), error.getDefaultMessage());
//        }
//        message.put("error", errorMap);
//        return new ResponseEntity<>(message, status);
//    }

    public static ResponseEntity<Object> errors (BindingResult bindingResult, HttpStatus status) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> errorMap = new HashMap<>();

        message.put("status", status.name());
        message.put("code", String.valueOf(status.value()));

        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }

        message.put("error", errorMap);
        return new ResponseEntity<>(message, status);
    }

}
