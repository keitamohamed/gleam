package com.keita.gleam.controller;

import com.keita.gleam.model.Subject;
import com.keita.gleam.service.SubjectDOAImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/gleam/subject")
public class SubjectController {

    private final SubjectDOAImp subjectDOAImp;

    @Autowired
    public SubjectController(SubjectDOAImp subjectDOAImp) {
        this.subjectDOAImp = subjectDOAImp;
    }

    @PostMapping(path = {"/save"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody Subject subject, BindingResult bindingResult) {
        return subjectDOAImp.save(subject, bindingResult);
    }
}
