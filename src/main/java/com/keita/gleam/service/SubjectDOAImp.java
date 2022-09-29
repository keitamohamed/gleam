package com.keita.gleam.service;

import com.keita.gleam.doa.SubjectDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.model.Subject;
import com.keita.gleam.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class SubjectDOAImp {

    private final SubjectDOA subjectDOA;

    @Autowired
    public SubjectDOAImp(SubjectDOA subjectDOA) {
        this.subjectDOA = subjectDOA;
    }

    public ResponseEntity<?> save(Subject subject, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return InvalidInput.errors(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }

        long id = Long.parseLong(Util.generateFourDigit());
        while (subjectDOA.existsBySubjectID(id)) {
            id = Long.parseLong(Util.generateFourDigit());
        }
        subject.setSubjectID(id);
        Subject saveResponse = subjectDOA.save(subject);
        String message = String.format("%s subject have been added", saveResponse.getName());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
