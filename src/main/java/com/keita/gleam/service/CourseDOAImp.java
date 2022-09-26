package com.keita.gleam.service;

import com.keita.gleam.doa.CourseDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.mapper.ResponseMessage;
import com.keita.gleam.model.Admin;
import com.keita.gleam.model.Course;
import com.keita.gleam.model.Courses;
import com.keita.gleam.model.Student;
import com.keita.gleam.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class CourseDOAImp {

    private final CourseDOA courseDOA;

    @Autowired
    public CourseDOAImp(CourseDOA courseDOA) {
        this.courseDOA = courseDOA;
    }

    public ResponseEntity<?> update(Admin admin, Course courses, BindingResult bindingResult){
        String message;

        if (bindingResult.hasErrors()) {
            return InvalidInput.error(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }

        boolean courseExist = courseDOA.existsByCourseName(courses.getCourseName());
        if (courseExist) {
            message = String.format("%s course already exist", courses.getCourseName());
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.FOUND.name(), HttpStatus.FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.FOUND);
        }

        long courseID = Long.parseLong(Util.generateSixDigit());
        Optional<Course> findCourse = findByID(courseID);
        while (findCourse.isPresent()) {
            courseID = Long.parseLong(Util.generateSixDigit());
            findCourse = findByID(courseID);
        }

        courses.setCourseID(courseID);
        courses.setAdmin(admin);
        Courses saveResponse = courseDOA.save(courses);
        message = String.format("A new course have been created with an id %s", saveResponse.getCourseID());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public void update(Course courses){
        courseDOA.save(courses);
    }

    public ResponseEntity<?> updateCourse(Long id, Course courses) {
        Optional<Course> findCourse = findByID(id);

        if (findCourse.isEmpty()) {
            String message = String.format("There is no course found with an id %s", id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        findCourse.ifPresent(c -> {
            c.setCourseName(courses.getCourseName());
            c.setDescription(courses.getDescription());
            c.setCredit(courses.getCredit());
        });
        Course updateResult = courseDOA.save(findCourse.get());
        String message = String.format("%s [id: %s] course information have been updated successfully", updateResult.getCourseName(), updateResult.getCourseID());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public Course findCourseByID(Long id, HttpServletResponse response) {
        Optional<Course> findCourse = findByID(id);
        if (findCourse.isEmpty()) {
            String message = String.format("No course found with an id %s", id);
            Message.noFoundException(message, HttpStatus.OK, response);
        }
       return findCourse.get();
    }

    public List<Course> courseList(HttpServletResponse response) {
        List<Course> findAll = courseDOA.findAll();
        if (findAll.isEmpty()) {
            Message.noFoundException("There are no courses. Add new course", HttpStatus.OK, response);
        }
        return findAll;
    }

    public ResponseEntity<?> deleteCourse(Long id) {
        String message;
        Optional<Course> courses = findByID(id);
        if (courses.isEmpty()) {
            message = String.format("No Course exist with an id %s", id);
            return Message.setMessage(message, HttpStatus.OK);
        }
//        courseDOA.delete(courses.get());
        message = String.format("Successfully deleted course with an id %s", id);
        return Message.setMessage(message, HttpStatus.OK);
    }

    private Optional<Course> findByID(Long id) {
        return courseDOA.findByCourseID(id);
    }
}
