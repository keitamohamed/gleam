package com.keita.gleam.service;

import com.keita.gleam.doa.MajorDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.model.Admin;
import com.keita.gleam.model.Major;
import com.keita.gleam.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class MajorDOAImp {

    private final MajorDOA majorDOA;

    @Autowired
    public MajorDOAImp(MajorDOA majorDOA) {
        this.majorDOA = majorDOA;
    }

    public ResponseEntity<?> save(Major major, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return InvalidInput.errors(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }

        String message;
        boolean majorExist = majorDOA.existsByName(major.getName());
        if (majorExist) {
            message = String.format("%s major already exist", major.getName());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        long id = Long.parseLong(Util.generateSevenDigit());
        boolean idExist = majorDOA.existsByMajorID(id);
        while (idExist) {
            id = Long.parseLong(Util.generateSevenDigit());
            idExist = majorDOA.existsByMajorID(id);
        }
        major.setMajorID(id);
        Major saveResponse = majorDOA.save(major);
        message = String.format("Major %s have been save successfully", saveResponse.getName());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    public List<Major> majorList(HttpServletResponse response) {
        List<Major> majorList = majorDOA.findAll();
        if (majorList.size() == 0) {
            Message.noFoundException("Admin list is empty. Add new admin", HttpStatus.OK, response);
        }
        return majorList;
    }


}
