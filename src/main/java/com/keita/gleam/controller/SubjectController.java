package com.keita.gleam.controller;

import com.keita.gleam.model.Major;
import com.keita.gleam.model.Subject;
import com.keita.gleam.service.MajorDOAImp;
import com.keita.gleam.service.SubjectDOAImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/gleam/subject")
public class SubjectController {

    private final SubjectDOAImp subjectDOAImp;
    private final MajorDOAImp majorDOAImp;

    @Autowired
    public SubjectController(SubjectDOAImp subjectDOAImp, MajorDOAImp majorDOAImp) {
        this.subjectDOAImp = subjectDOAImp;
        this.majorDOAImp = majorDOAImp;
    }

    @PostMapping(path = {"/save"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(@Valid @RequestBody Subject subject, BindingResult bindingResult) {
        return subjectDOAImp.save(subject, bindingResult);
    }
    @PutMapping(path = {"/subject_set_major/{id}/{mID}"})
    public ResponseEntity<?> setMajor(@PathVariable Long id, @PathVariable Long mID, HttpServletResponse response) {
        Optional<Subject> subject = subjectDOAImp.findByID(id);
        Major major = majorDOAImp.setSubject(mID, subject.get(), response);
        return subjectDOAImp.update(id, major);
    }

    @GetMapping(path = {"/lists"})
    public List<Subject> subjectList(HttpServletResponse response) {
        return subjectDOAImp.subjectList(response);
    }
}
