package com.keita.gleam.service;

import com.keita.gleam.doa.AdminDOA;
import com.keita.gleam.mapper.FoundUser;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.model.Admin;
import com.keita.gleam.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class AdminDOAImp {
    private final AdminDOA adminDOA;

    @Autowired
    public AdminDOAImp(AdminDOA adminDOA) {
        this.adminDOA = adminDOA;
    }

    public ResponseEntity<Object> save(Admin admin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return InvalidInput.error(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }
        long id = Long.parseLong(Util.generateSixDigit());
        Optional<Admin> findByID = findAdminByID(id);
        while (findByID.isPresent()) {
            id = Long.parseLong(Util.generateSixDigit());
            findByID = findAdminByID(id);
        }
        String message = String.format("A new admin have been created with an id %s", admin.getAdminID());
        return Message.setMessage(admin.getAdminID(), message, HttpStatus.OK);
    }

    private Optional<Admin> findAdminByID(long id) {
        return adminDOA.getAdminByAdminID(id);
    }
}
