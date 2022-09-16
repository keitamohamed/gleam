package com.keita.gleam.service;

import com.keita.gleam.doa.TeacherDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.mapper.ResponseMessage;
import com.keita.gleam.model.Address;
import com.keita.gleam.model.Authenticate;
import com.keita.gleam.model.Teacher;
import com.keita.gleam.util.Util;
import com.keita.gleam.validate.NotEmptyAddressFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeacherDOAImp {

    private final TeacherDOA teacherDOA;

    @Autowired
    public TeacherDOAImp(TeacherDOA teacherDOA) {
        this.teacherDOA = teacherDOA;
    }

    public ResponseEntity<?> save(Teacher teacher, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return InvalidInput.error(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }
        long id = Long.parseLong(Util.generateSixDigit());
        Optional<Teacher> findTeacher = teacherDOA.findById(id);
        while (findTeacher.isPresent()) {
            id = Long.parseLong(Util.generateSixDigit());
            findTeacher = teacherDOA.findById(id);
        }
        teacher.setTeacherID(id);
        Authenticate authenticate = teacher.getAuth();
        authenticate.setTeacher(teacher);
        Set<Address> address = teacher.getAddress();
        teacher.setAddress(address);
        Teacher saveResponse = teacherDOA.save(teacher);
        String message = String.format("A new teacher have been created with an id %s", saveResponse.getTeacherID());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> update(Long id, Teacher teacher) {
        Optional<Teacher> findTeacher = teacherDOA.findById(id);
        String message;

        if (findTeacher.isEmpty()) {
            message = String.format("There are no student with an id %s", id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }

        findTeacher.ifPresent(teacher1 ->  {
            teacher1.setName(teacher.getName());
            teacher1.setDob(teacher.getDob());
            teacher1.setGender(teacher.getGender());
            teacher1.setPhone(teacher.getPhone());
        });
        Teacher updateResult = teacherDOA.save(findTeacher.get());
        message = String.format("Information was successfully updated for teacher with an id %s", updateResult.getTeacherID());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public List<Teacher> teacherList(HttpServletResponse response) {
        List<Teacher> findAll = teacherDOA.findAll();
        if (findAll.isEmpty()) {
            Message.noFoundException("Teacher list is empty. Add new teacher", HttpStatus.OK, response);
        }
        return findAll;
    }

    public ResponseEntity<?> deleteTeacher(Long id) {

        Optional<Teacher> findTeacher = teacherDOA.findById(id);
        String message;

        if (findTeacher.isEmpty()) {
            message = String.format("No teacher find to delete with an id %s", id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        teacherDOA.delete(findTeacher.get());
        message = String.format("Successfully deleted teacher with an id %s", id);
        return Message.setMessage(message, HttpStatus.OK);
    }

    public Teacher findAdminByID(Long id, HttpServletResponse response) {
        Teacher findTeacher = findByID(id);
        if (findTeacher == null) {
            String message = String.format("Could not find teacher with an id %s", id);
            Message.noFoundException(message, HttpStatus.OK, response);
        }
        return findTeacher;
    }

    private Teacher findByID(Long id) {
        return teacherDOA.getTeacherByTeacherID(id);
    }
}
