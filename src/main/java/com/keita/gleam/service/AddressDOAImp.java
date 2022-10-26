package com.keita.gleam.service;

import com.keita.gleam.doa.AddressDOA;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.mapper.ResponseMessage;
import com.keita.gleam.model.Address;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class AddressDOAImp {

    private final AddressDOA addressDOA;

    public AddressDOAImp(AddressDOA addressDOA) {
        this.addressDOA = addressDOA;
    }

    public Optional<Address> findByID(Long id, HttpServletResponse response) {
        Optional<Address> findAddress = addressDOA.findAddressByAddressID(id);
        if (findAddress.isEmpty()) {
            Message.noFoundException(message(id), HttpStatus.OK, response);
        }
        return findAddress;
    }

    public ResponseEntity<?> save(Address address, String message) {
        addressDOA.save(address);
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteAddress(Address address) {
        Address findAddress = addressDOA.findAddressByID(address.getAddressID());
        if (findAddress == null) {
            ResponseMessage responseMessage = new ResponseMessage(message(address.getAddressID()), HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        String message = String.format("Address with an id %s have been successfully deleted", address.getAddressID());
        addressDOA.delete(findAddress);
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    private String message(Long id) {
        return String.format("No address exist with an id %s", id);
    }
}
