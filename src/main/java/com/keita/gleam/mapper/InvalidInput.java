package com.keita.gleam.mapper;

import com.keita.gleam.model.Address;
import com.keita.gleam.model.Admin;
import com.keita.gleam.validate.NotEmptyAddressFields;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvalidInput {

    public InvalidInput () {}

    public static ResponseEntity<Object> error (BindingResult bindingResult, List<Address> addresses, HttpStatus status) {
        Map<String, Object> message = new HashMap<>();
        Map<String, Object> errorMap = new HashMap<>();
        Map<String, String> addressError = new HashMap<>();

        message.put("status", status.name());
        message.put("code", String.valueOf(status.value()));
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        if (!NotEmptyAddressFields.isAddressValid(addresses)) {
            for (Address address : addresses) {
                if (address.getStreet().trim().isEmpty()) {
                    addressError.put("street", "Enter valid street address");
                }
                if (address.getCity().trim().isEmpty()) {
                    addressError.put("city", "Enter valid city name");
                }
                if (address.getState().trim().isEmpty()) {
                    addressError.put("state", "Enter valid state name");
                }
                if (address.getZip() == 0 || String.valueOf(address.getZip()).length() != 5) {
                    addressError.put("zipCode", "Zip code must be 5 digit number");
                }
            }
            errorMap.put("address", addressError);
        }
        message.put("error", errorMap);
        return new ResponseEntity<>(message, status);
    }

}
