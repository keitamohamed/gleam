package com.keita.gleam.service;

import com.keita.gleam.doa.AdminDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.mapper.ResponseMessage;
import com.keita.gleam.model.Admin;
import com.keita.gleam.model.Authenticate;
import com.keita.gleam.util.Util;
import com.keita.gleam.validate.NotEmptyAddressFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class AdminDOAImp {
    private final AdminDOA adminDOA;

    @Autowired
    public AdminDOAImp(AdminDOA adminDOA) {
        this.adminDOA = adminDOA;
    }

    public ResponseEntity<?> save(Admin admin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return InvalidInput.error(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }

        long id = Long.parseLong(Util.generateSixDigit());
        Optional<Admin> findByID = findAdminByID(id);
        while (findByID.isPresent()) {
            id = Long.parseLong(Util.generateSixDigit());
            findByID = findAdminByID(id);
        }
        admin.setAdminID(id);
        Authenticate authenticate = admin.getAuth();
        authenticate.setAuth(admin);
        Admin saveResponse = adminDOA.save(admin);
        String message = String.format("A new admin have been created with an id %s", saveResponse.getAdminID());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessage> update(Long id, Admin admin) {
        Optional<Admin> findAdmin = findAdminByID(id);
        ResponseMessage responseMessage;
        String message;
        if (findAdmin.isEmpty()) {
            message = String.format("There are no admin with an id %s", id);
            responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }

        findAdmin.ifPresent(result -> {
            result.setName(admin.getName());
            result.setGender(admin.getGender());
            result.setPhone(admin.getPhone());
            result.setDob(admin.getDob());
        });
        Admin updateResult = adminDOA.save(findAdmin.get());
        message = String.format("Information was successfully updated for admin with an id %s", updateResult.getAdminID());
        responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public Optional<Admin> findByID(Long id, HttpServletResponse response) {
        Optional<Admin> admin = findAdminByID(id);
        if (admin.isEmpty()) {
            String message = String.format("Could not find admin with an id %s", id);
            Message.noFoundException(message, HttpStatus.OK, response);
        }
        return admin;
    }

    public List<Admin> adminList(HttpServletResponse response) {
        List<Admin> adminList = adminDOA.findAll();
        if (adminList.size() == 0) {
          Message.noFoundException("Admin list is empty. Add new admin", HttpStatus.OK, response);
        }
        return adminList;
    }

    public ResponseEntity<?> deleteAdmin(Long id) {
        String message;
        Optional<Admin> admin = findAdminByID(id);
        if (admin.isEmpty()) {
            message = String.format("No admin find to delete with an id %s", id);
            return Message.setMessage(message, HttpStatus.OK);
        }
        adminDOA.delete(admin.get());
        message = String.format("Successfully deleted admin with an id %s", id);
        return Message.setMessage(message, HttpStatus.OK);
    }

    private Optional<Admin> findAdminByID(long id) {
        return adminDOA.getAdminByAdminID(id);
    }
}
