package com.keita.gleam.controller;

import com.keita.gleam.model.Major;
import com.keita.gleam.model.Student;
import com.keita.gleam.service.MajorDOAImp;
import com.keita.gleam.service.StudentDOAImp;
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
@CrossOrigin
@RequestMapping("/gleam/student")
public class StudentController {

    private final StudentDOAImp studentDOAImp;
    private final MajorDOAImp majorDOAImp;

    @Autowired
    public StudentController(StudentDOAImp studentDOAImp, MajorDOAImp majorDOAImp) {
        this.studentDOAImp = studentDOAImp;
        this.majorDOAImp = majorDOAImp;
    }

    @PostMapping(value = {"/save"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(
            @Valid
            @RequestBody
            Student student,
            BindingResult bindingResult
            ) {
        return studentDOAImp.save(student, bindingResult);
    }

    @PutMapping(path = {"/enroll_in_major/{id}/{mID}"})
    public ResponseEntity<?> addMajor(@PathVariable Long id, @PathVariable Long mID, HttpServletResponse response) {
        Optional<Major> major = majorDOAImp.findByID(mID, response);
        return studentDOAImp.addMajor(id, major);
    }

    @PutMapping(value = {"/enroll-in-course/{id}/{cid}"})
    public ResponseEntity<?> addCourse(@PathVariable Long id, @PathVariable Long cid, HttpServletResponse response) {
        return studentDOAImp.addCourse(id, cid, response);
    }

    @GetMapping(value = {"/find_by_id/{id}"})
    public Student findByID(@PathVariable Long id, HttpServletResponse response) {
        return studentDOAImp.findStudentByID(id, response);
    }

    @GetMapping(path = {"/find_all"})
    public List<Student> studentList(HttpServletResponse response) {
        return studentDOAImp.findAllStudent(response);
    }

    @PutMapping(path = {"/update/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        return studentDOAImp.update(id, student);
    }

    @PutMapping(path = {"/remove_major/{id}/{mID}"})
    public ResponseEntity<?> removeMajor(@PathVariable Long id, @PathVariable Long mID) {
        return studentDOAImp.removeMajor(id, mID);
    }

    @DeleteMapping(path = {"/delete_by_id/{id}"})
    public ResponseEntity<?> deleteStudentByID(@PathVariable Long id) {
        return studentDOAImp.deleteStudentByID(id);
    }

}
