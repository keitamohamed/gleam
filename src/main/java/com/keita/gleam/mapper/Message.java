package com.keita.gleam.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class Message {

    public static ResponseEntity<Object> setMessage(String message, HttpStatus status) {

        Map<String, Object> mapper = new HashMap<>();

        mapper.put("status", status.name());
        mapper.put("code", String.valueOf(status.value()));

        mapper.put("message", message);
        return new ResponseEntity<>(mapper, status);
    }

    public static ResponseEntity<Object> responseMessage(String message, HttpStatus status) {
        Map<String, String> map = new HashMap<>();
        map.put("status", status.name());
        map.put("code", String.valueOf(status.value()));
        map.put("message", message);
        return new ResponseEntity<>(map, status);
    }

    public static void noFoundException(String message, HttpStatus status, HttpServletResponse response) {
        try {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("status", status.name());
            errorMap.put("code", String.valueOf(status.value()));
            errorMap.put("message", message);
            response.setStatus(status.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            new MessageMapper(response.getOutputStream(), errorMap);
        }catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }
}
