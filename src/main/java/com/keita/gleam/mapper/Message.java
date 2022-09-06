package com.keita.gleam.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class Message {

    public static ResponseEntity<Object> setMessage(Long id, String message, HttpStatus status) {

        Map<String, Object> mapper = new HashMap<>();

        mapper.put("status", status.name());
        mapper.put("code", String.valueOf(status.value()));

        mapper.put("message", message);
        mapper.put("id", id);
        return new ResponseEntity<>(mapper, status);
    }
}
