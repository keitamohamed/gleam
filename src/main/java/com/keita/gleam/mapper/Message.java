package com.keita.gleam.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
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

    public static ResponseEntity<Object> userAlreadyExist(String message, HttpStatus status) {
        Map<String, String> map = new HashMap<>();
        map.put("status", status.name());
        map.put("code", String.valueOf(status.value()));
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }
}
