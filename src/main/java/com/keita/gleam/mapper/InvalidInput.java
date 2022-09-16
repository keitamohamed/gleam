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
        for (FieldError error : bindingResult.getFieldErrors()) {
            if (error.getField().contains("address")) {
                String replace = error.getField().trim().replace("address["+ index + "].", "");
                addressError.put(replace, error.getDefaultMessage());
                count += 1;
            } else {
                errorMap.put(error.getField(), error.getDefaultMessage());
                System.out.println(error.getField() + " Message: " + error.getDefaultMessage());
            }
            if (count == 4) {
                count = 0;
                index++;
            }
        }
//        if (!NotEmptyAddressFields.isAddressValid(addresses)) {
//            for (Address address : addresses) {
//                if (address.getStreet().trim().isEmpty()) {
//                    addressError.put("street", "Enter valid street address");
//                }
//                if (address.getCity().trim().isEmpty()) {
//                    addressError.put("city", "Enter valid city name");
//                }
//                if (address.getState().trim().isEmpty()) {
//                    addressError.put("state", "Enter valid state name");
//                }
//                if (address.getZip() == 0 || String.valueOf(address.getZip()).length() != 5) {
//                    addressError.put("zipCode", "Zip code must be 5 digit number");
//                }
//            }
//            errorMap.put("address", addressError);
//        }
        errorMap.put("address", addressError);
        message.put("error", errorMap);
        return new ResponseEntity<>(message, status);
    }

}
