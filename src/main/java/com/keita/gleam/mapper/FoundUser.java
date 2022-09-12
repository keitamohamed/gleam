package com.keita.gleam.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FoundUser {

    public static ResponseEntity<Object> userExist(String message,  HttpStatus status) {
        return Message.responseMessage(message, status);
    }
}
