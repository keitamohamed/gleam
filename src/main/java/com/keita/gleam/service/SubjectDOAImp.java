package com.keita.gleam.service;

import com.keita.gleam.doa.SubjectDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.mapper.ResponseMessage;
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
import java.util.Set;

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
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public Optional<Subject> findByID(Long id) {
        return subjectDOA.findById(id);
    }

    public ResponseEntity<?> update(Long id, Subject subject) {
        Optional<Subject> findSubject = subjectDOA.findById(id);
        if (findSubject.isEmpty()) {
            String message = String.format("No subject exist with an id %s", id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
        }
        findSubject.ifPresent(s -> {
            s.setDescription(subject.getDescription());
            s.setName(subject.getName());
        });
        Subject saveResponse = subjectDOA.save(findSubject.get());
        String message = String.format("%s subject have benn updated", saveResponse.getName());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> update(Long id, Major major) {
        Optional<Subject> findSubject = subjectDOA.findById(id);
        if (findSubject.isEmpty()) {
            String message = String.format("No subject exist with an id %s", id);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        Subject subject = findSubject.get();
        subject.addNewMajor(major);

        Subject updateResponse = subjectDOA.save(findSubject.get());
        String message = String.format("%s have been update", updateResponse.getName());
        return new ResponseEntity<>(new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value()), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteSubject(Long id) {
        boolean isMajorsRemoved = removeMajor(id);
        Optional<Subject> subject = subjectDOA.findById(id);
        if (!isMajorsRemoved || subject.isEmpty()) {
            String message = String.format("Could not delete subject with an id %s", id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
        }
        String message = String.format("%s subject have been deleted", subject.get().getName());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    private boolean removeMajor(Long id) {
        Optional<Subject> findSubject = subjectDOA.findById(id);
        if (findSubject.isEmpty()) {
            return false;
        }
        Set<Major> majors = findSubject.get().getMajor();
        findSubject.get().removeMajors(majors);
        subjectDOA.save(findSubject.get());
        return true;
    }

    public List<Subject> subjectList(HttpServletResponse response) {
        List<Subject> findAll = subjectDOA.findAll();
        if (findAll.isEmpty()) {
            Message.noFoundException("Subject list is empty. Add new subject", HttpStatus.OK, response);
        }
        return findAll;
    }
}
