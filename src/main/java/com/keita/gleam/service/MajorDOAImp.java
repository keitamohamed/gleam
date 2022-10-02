package com.keita.gleam.service;

import com.keita.gleam.doa.MajorDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.mapper.MessageMapper;
import com.keita.gleam.model.Major;
import com.keita.gleam.model.Subject;
import com.keita.gleam.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

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

    public Optional<Major> findByID(Long id, HttpServletResponse response) {
        Optional<Major> findMajor = majorDOA.findById(id);
        if (findMajor.isEmpty()) {
            String message = String.format("Could not find major with an id %s", id);
            Message.noFoundException(message, HttpStatus.OK, response);
        }
        return findMajor;
    }

    public ResponseEntity<?> update(Long id, Major major) {

        Optional<Major> findMajor = majorDOA.findById(id);
        findMajor.ifPresent(m -> m.setDescription(major.getDescription()));

        String message;
        if (findMajor.isPresent()) {
            majorDOA.save(findMajor.get());
            message = String.format("%s description have been updated successfully", findMajor.get().getName());
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        message = String.format("No major found for id %s", id);
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    public Major setSubject(Long id, Subject subject, HttpServletResponse response) {
        Optional<Major> find = findByID(id, response);
        if (find.isEmpty()) {
            Message.noFoundException(String.format("No major find with an id %s", id), HttpStatus.OK, response);
        }
        Major major = find.get();
        major.addNewSubject(subject);
        return (majorDOA.save(major));
    }

    public List<Major> majorList(HttpServletResponse response) {
        List<Major> majorList = majorDOA.findAll();
        if (majorList.size() == 0) {
            Message.noFoundException("Major list is empty. Add new major", HttpStatus.OK, response);
        }
        return majorList;
    }


}
