package com.keita.gleam.controller;

import com.keita.gleam.mapper.ResponseMessage;
import com.keita.gleam.model.Admin;
import com.keita.gleam.model.Course;
import com.keita.gleam.service.AdminDOAImp;
import com.keita.gleam.service.CourseDOAImp;
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
@RequestMapping("/gleam/admin")
public class AdminController {

    private final AdminDOAImp adminDOAImp;
    private final CourseDOAImp courseDOAImp;

    @Autowired
    public AdminController(AdminDOAImp adminDOAImp, CourseDOAImp courseDOAImp) {
        this.adminDOAImp = adminDOAImp;
        this.courseDOAImp = courseDOAImp;
    }

    @PostMapping(value = {"/save"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> save(
            @Valid
            @RequestBody
            Admin admin,
            BindingResult bindingResult ) {
        return adminDOAImp.save(admin, bindingResult);
    }

    @PostMapping(value = {"/save_course/{id}"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCourse(
            @Valid
            @RequestBody
            Course courses,
            BindingResult bindingResult,
            @PathVariable Long id,
            HttpServletResponse response) {
        Optional<Admin> findAdmin = adminDOAImp.findByID(id, response);
        Admin admin = findAdmin.get();
        return courseDOAImp.update(admin, courses, bindingResult);
    }

    @PutMapping(
            value = {"/update/{id}"},
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseMessage> updateAdmin(@RequestBody Admin admin, @PathVariable Long id) {
        return adminDOAImp.update(id, admin);
    }
    @GetMapping(value = {"/find_by_id/{id}"})
    public Optional<Admin> findByID(@PathVariable Long id, HttpServletResponse response) {
        return adminDOAImp.findByID(id, response);
    }
    @DeleteMapping(value = {"/delete_admin/{id}"})
    public ResponseEntity<?> deleteAdmin(@PathVariable Long id) {
        return adminDOAImp.deleteAdmin(id);
    }
    @GetMapping("/all")
    public List<Admin> adminList(HttpServletResponse response) {
        return adminDOAImp.adminList(response);
    }
}
