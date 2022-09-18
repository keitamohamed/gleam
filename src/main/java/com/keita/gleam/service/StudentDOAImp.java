package com.keita.gleam.service;

import com.keita.gleam.doa.StudentDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.mapper.ResponseMessage;
import com.keita.gleam.model.Authenticate;
import com.keita.gleam.model.Course;
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
public class StudentDOAImp {

    private final StudentDOA studentDOA;
    private final CourseDOAImp courseDOAImp;

    @Autowired
    public StudentDOAImp(StudentDOA studentDOA, CourseDOAImp courseDOAImp) {
        this.studentDOA = studentDOA;
        this.courseDOAImp = courseDOAImp;
    }

    public ResponseEntity<?> save(Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return InvalidInput.error(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }
        long id = Long.parseLong(Util.generateSixDigit());
        Optional<Student> findStudent = findByID(id);
        while (findStudent.isPresent()) {
            id = Long.parseLong(Util.generateSixDigit());
            findStudent = findByID(id);
        }
        student.setStudentID(id);
        Authenticate authenticate = student.getAuth();
        authenticate.setStudent(student);
        Student saveResponse = studentDOA.save(student);
        String message = String.format("A new student have been created with an id %s", saveResponse.getStudentID());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> addCourse(Long id, Long courseID, HttpServletResponse response) {
        Optional<Student> findStudent = findByID(id);
        Course courses = courseDOAImp.findCourseByID(courseID, response);
        ResponseMessage responseMessage;
        if (findStudent.isEmpty()) {
            String message = String.format("No Student found with an id %s", courseID);
            responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        findStudent.ifPresent(student -> student.addNewCourse(courses));
        courses.AddNewStudent(findStudent.get());
        Student saveResponse = studentDOA.save(findStudent.get());

        courseDOAImp.update(courses);
        String message = String.format("%s have been add in %s class", saveResponse.getName(), courses.getCourseName());
        responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> update(Long id, Student student) {
        Optional<Student> findStudent = findByID(id);

        if (findStudent.isEmpty()) {
            String message = String.format("There are no student with an id %s", id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }

        findStudent.ifPresent(student1 -> {
            student.setName(student.getName());
            student1.setDob(student.getDob());
            student1.setGender(student.getGender());
            student1.setPhone(student.getPhone());
        });
        Student updateResponse = studentDOA.save(findStudent.get());
        String message = String.format("Information was successfully updated for student with an id %s", updateResponse.getStudentID());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public List<Student> findAllStudent(HttpServletResponse response) {
        List<Student> findAll = studentDOA.findAll();
        if (findAll.isEmpty()) {
            Message.noFoundException("Student list is empty. Add new teacher", HttpStatus.OK, response);
        }
        return findAll;
    }

    public ResponseEntity<?> deleteStudentByID(Long id) {
        Optional<Student> findStudent = findByID(id);
        if (findStudent.isEmpty()) {
            String message = String.format("No student find to delete with an id %s", id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        studentDOA.delete(findStudent.get());
        String message = String.format("Successfully deleted student with an id %s", id);
        return Message.setMessage(message, HttpStatus.OK);
    }

    public Student findStudentByID(Long id, HttpServletResponse response) {
        Optional<Student> student = findByID(id);
        if (student.isEmpty()) {
            String message = String.format("Could not find student with an id %s", id);
            Message.noFoundException(message, HttpStatus.OK, response);
        }
        return student.orElse(new Student());
    }
    private Optional<Student> findByID(Long id) {
        return studentDOA.findById(id);
    }
}
