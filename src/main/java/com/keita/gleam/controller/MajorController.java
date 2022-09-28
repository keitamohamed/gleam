package com.keita.gleam.controller;

import com.keita.gleam.model.Major;
import com.keita.gleam.service.MajorDOAImp;
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
@RequestMapping("/gleam/major")
public class MajorController {

    private final MajorDOAImp majorDOAImp;

    @Autowired
    public MajorController(MajorDOAImp majorDOAImp) {
        this.majorDOAImp = majorDOAImp;
    }

    @PostMapping(value = {"/save"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addNewMajor(
            @Valid
            @RequestBody
            Major major,
            BindingResult bindingResult) {
        return majorDOAImp.save(major, bindingResult);
    }

    @PutMapping(path = {"/update/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody Major major, @PathVariable Long id) {
        return majorDOAImp.update(id, major);
    }

    @GetMapping(path = {"/find_by_id/{id}"})
    public Optional<Major> findByID(@PathVariable Long id, HttpServletResponse response) {
        return majorDOAImp.findByID(id, response);
    }

    @GetMapping(value = {"/lists"})
    public List<Major> majorList(HttpServletResponse response) {
        return majorDOAImp.majorList(response);
    }

}
