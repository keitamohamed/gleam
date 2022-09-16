package com.keita.gleam.controller;

import com.keita.gleam.model.Courses;
import com.keita.gleam.model.Student;
import com.keita.gleam.service.CourseDOAImp;
import com.keita.gleam.service.StudentDOAImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/gleam/student")
public class StudentController {

    private final StudentDOAImp studentDOAImp;

    @Autowired
    public StudentController(StudentDOAImp studentDOAImp) {
        this.studentDOAImp = studentDOAImp;
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

    @PutMapping(value = {"/add_course/{id}/{cid}"})
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

    @DeleteMapping(path = {"/delete_by_id/{id}"})
    public ResponseEntity<?> deleteStudentByID(@PathVariable Long id) {
        return studentDOAImp.deleteStudentByID(id);
    }

}
