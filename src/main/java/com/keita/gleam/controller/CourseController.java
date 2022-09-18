package com.keita.gleam.controller;

import com.keita.gleam.model.Course;
import com.keita.gleam.service.CourseDOAImp;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/gleam/course")
public class CourseController {

    private final CourseDOAImp courseDOAImp;
    public CourseController(CourseDOAImp courseDOAImp) {
        this.courseDOAImp = courseDOAImp;
    }

    @PutMapping(path = {"/update_course/{id}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course courses) {
        return courseDOAImp.updateCourse(id, courses);
    }

    @GetMapping(path = {"/find_by_id/{id}"})
    public Course findCourseByID(@PathVariable Long id, HttpServletResponse response) {
        return courseDOAImp.findCourseByID(id, response);
    }

    @GetMapping(path = {"/all"})
    public List<Course> findCourseByID(HttpServletResponse response) {
        return courseDOAImp.courseList(response);
    }

    @DeleteMapping(value = {"/delete/{id}"})
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        return courseDOAImp.deleteCourse(id);
    }
}
