package com.keita.gleam.controller;

import com.keita.gleam.model.Course;
import com.keita.gleam.model.Subject;
import com.keita.gleam.service.CourseDOAImp;
import com.keita.gleam.service.SubjectDOAImp;
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
@RequestMapping("/gleam/course")
public class CourseController {

    private final CourseDOAImp courseDOAImp;
    private final SubjectDOAImp subjectDOAImp;

    public CourseController(CourseDOAImp courseDOAImp, SubjectDOAImp subjectDOAImp) {
        this.courseDOAImp = courseDOAImp;
        this.subjectDOAImp = subjectDOAImp;
    }

    @PostMapping(path = {"/save/{id}"},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveCourse(
            @Valid
            @RequestBody
            Course courses,
            @PathVariable Long id,
            BindingResult bindingResult) {
        Optional<Subject> subject = subjectDOAImp.findByID(id);
        return courseDOAImp.save(courses, subject, bindingResult);
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
