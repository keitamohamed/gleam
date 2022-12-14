package com.keita.gleam.controller;

import com.keita.gleam.model.Address;
import com.keita.gleam.model.Course;
import com.keita.gleam.model.Teacher;
import com.keita.gleam.service.CourseDOAImp;
import com.keita.gleam.service.TeacherDOAImp;
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
@RequestMapping(path = "/gleam/teacher")
public class TeacherController {

    private final TeacherDOAImp teacherDOAImp;
    private final CourseDOAImp courseDOAImp;

    @Autowired
    public TeacherController(TeacherDOAImp teacherDOAImp, CourseDOAImp courseDOAImp) {
        this.teacherDOAImp = teacherDOAImp;
        this.courseDOAImp = courseDOAImp;
    }

    @PostMapping(value = {"/save"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(
            @Valid
            @RequestBody
            Teacher teacher,
            BindingResult bindingResult) {
        return teacherDOAImp.save(teacher, bindingResult);
    }

    @PostMapping(path = {"/add-address/{id}"})
    public ResponseEntity<?> saveAddress(
            @Valid
            @RequestBody Address address, BindingResult bindingResult, @PathVariable Long id) {
       return teacherDOAImp.saveAddress(id, address, bindingResult);
    }

    @PutMapping(value = {"/add_course/{id}/{cid}"})
    public ResponseEntity<?> addCourse(@PathVariable Long id, @PathVariable Long cid) {
        Course course = courseDOAImp.isSave(cid, teacherDOAImp.findById(id));
        return teacherDOAImp.updateSetCourse(id, cid, course);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody Teacher teacher, @PathVariable Long id) {
        return teacherDOAImp.update(id, teacher);
    }

    @PutMapping(path = {"/update-address"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAddress(
            @Valid
            @RequestBody Address address,
            BindingResult bindingResult,
            HttpServletResponse response) {
        return teacherDOAImp.updateAddress(address, bindingResult, response);
    }

    @GetMapping(value = {"/find_by_id/{id}"})
    public Teacher findTeacherByID(@PathVariable Long id, HttpServletResponse response) {
        return teacherDOAImp.findAdminByID(id, response);
    }

    @GetMapping(value = "/find_all")
    public List<Teacher> findAll(HttpServletResponse response) {
        return teacherDOAImp.teacherList(response);
    }

    @DeleteMapping(value = "/delete_by_id/{id}")
    public ResponseEntity<?> deleteTeacherByID(@PathVariable Long id) {
        return teacherDOAImp.deleteTeacher(id);
    }

    @DeleteMapping(path = {"/delete-address/{id}/{aid}"})
    public ResponseEntity<?> deleteAddress(@PathVariable Long id, @PathVariable Long aid) {
        return teacherDOAImp.removeAddress(id, aid);
    }

    @PutMapping(path = {"/remove_course/{id}/{cid}"})
    public ResponseEntity<?> removeCourse(@PathVariable Long id, @PathVariable Long cid) {
        return teacherDOAImp.updateRemoveCourse(id, cid);
    }

    @PutMapping(path = {"/remove_courses/{id}"})
    public ResponseEntity<?> removeAllCourse(@PathVariable Long id) {
        return teacherDOAImp.removeAllCourse(id);
    }

}
