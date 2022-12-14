package com.keita.gleam.service;

import com.keita.gleam.doa.CourseDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.mapper.ResponseMessage;
import com.keita.gleam.model.Course;
import com.keita.gleam.model.Courses;
import com.keita.gleam.model.Subject;
import com.keita.gleam.model.Teacher;
import com.keita.gleam.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CourseDOAImp {

    private final CourseDOA courseDOA;

    @Autowired
    public CourseDOAImp(CourseDOA courseDOA) {
        this.courseDOA = courseDOA;
    }

    public ResponseEntity<?> save(Course courses, Optional<Subject> subject, BindingResult bindingResult){

        String message;

        if (bindingResult.hasErrors()) {
            return InvalidInput.error(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }

        boolean courseExist = courseDOA.existsByName(courses.getName());
        if (courseExist) {
            message = String.format("%s course already exist", courses.getName());
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.FOUND.name(), HttpStatus.FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.FOUND);
        }

        if (subject.isEmpty()) {
            ResponseMessage responseMessage = new ResponseMessage(
                    String.format("Could not add %s course", courses.getName()), HttpStatus.OK.name(), HttpStatus.OK.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }

        long courseID = Long.parseLong(Util.generateSixDigit());
        Optional<Course> findCourse = findByID(courseID);
        while (findCourse.isPresent()) {
            courseID = Long.parseLong(Util.generateSixDigit());
            findCourse = findByID(courseID);
        }

        courses.setId(courseID);
        courses.setSubject(subject.get());
        Courses saveResponse = courseDOA.save(courses);
        message = String.format("A new course have been created, %s", saveResponse.getName());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public void update(Course courses){
        courseDOA.save(courses);
    }

    public Course isSave(Long id, Optional<Teacher> teacher) {
        Optional<Course> findCourse = findByID(id);
        if (teacher.isEmpty() || findCourse.isEmpty()) {
            return null;
        }
        findCourse.get().addTeacher(teacher.get());
        return (courseDOA.save(findCourse.get()));
    }

    public ResponseEntity<?> updateCourse(Long id, Course courses) {
        Optional<Course> findCourse = findByID(id);

        if (findCourse.isEmpty()) {
            String message = String.format("There is no course found with an id %s", id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        findCourse.ifPresent(c -> {
            c.setName(courses.getName());
            c.setDescription(courses.getDescription());
            c.setCredit(courses.getCredit());
        });
        Course updateResult = courseDOA.save(findCourse.get());
        String message = String.format("%s course information have been updated successfully", updateResult.getName());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public void removeTeacher(Teacher teacher) {
        Set<Course> courses = teacher.getCourses();
        courses.forEach(c -> {
            c.removeTeacher(teacher);
        });
    }

    public Course findCourseByID(Long id, HttpServletResponse response) {
        Optional<Course> findCourse = findByID(id);
        if (findCourse.isEmpty()) {
            String message = String.format("No course found with an id %s", id);
            Message.noFoundException(message, HttpStatus.OK, response);
            return null;
        }
       return findCourse.get();
    }

    public List<Course> courseList(HttpServletResponse response) {
        List<Course> findAll = courseDOA.getCourses();
        if (findAll.isEmpty()) {
            Message.noFoundException("There are no courses. Add new course", HttpStatus.OK, response);
        }
        return filter(findAll);
    }

    private List<Course> filter (List<Course> courses) {
        List<Course> filterC = new ArrayList<>();
        for (Course c : courses) {
            for (Teacher t : c.getTeachers()) {
                t.getCourses().removeAll(t.getCourses());
                t.getAddress().removeAll(t.getAddress());
                t.setAuth(null);
                t.setPhone(null);
                t.setDob(null);
            }
            filterC.add(c);
        }
        return filterC;
    }

    public ResponseEntity<?> deleteCourse(Long id) {
        String message;
        Optional<Course> courses = findByID(id);
        if (courses.isEmpty()) {
            message = String.format("No Course exist with an id %s", id);
            return Message.setMessage(message, HttpStatus.OK);
        }
        
        message = String.format("Successfully deleted %s course", courses.get().getName());
        courseDOA.delete(courses.get());
        return Message.setMessage(message, HttpStatus.OK);
    }

    public Optional<Course> findByID(Long id) {
        return courseDOA.findById(id);
    }
}
