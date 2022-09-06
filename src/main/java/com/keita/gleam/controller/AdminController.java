package com.keita.gleam.controller;

import com.keita.gleam.model.Admin;
import com.keita.gleam.service.AdminDOAImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/gleam/admin")
public class AdminController {

    private final AdminDOAImp adminDOAImp;

    @Autowired
    public AdminController(AdminDOAImp adminDOAImp) {
        this.adminDOAImp = adminDOAImp;
    }

    @PostMapping(value = {"/save"},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> save(
            @Valid
            @RequestBody
            Admin admin,
            BindingResult bindingResult ) {
        return adminDOAImp.save(admin, bindingResult);
    }
}
