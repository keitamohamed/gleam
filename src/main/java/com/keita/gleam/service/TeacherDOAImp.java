package com.keita.gleam.service;

import com.keita.gleam.doa.TeacherDOA;
import com.keita.gleam.mapper.InvalidInput;
import com.keita.gleam.mapper.Message;
import com.keita.gleam.mapper.ResponseMessage;
import com.keita.gleam.model.*;
import com.keita.gleam.util.Util;
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
    private final CourseDOAImp courseDOAImp;
    private final AddressDOAImp addressDOAImp;

    @Autowired
    public TeacherDOAImp(TeacherDOA teacherDOA, CourseDOAImp courseDOAImp, AddressDOAImp addressDOAImp) {
        this.teacherDOA = teacherDOA;
        this.courseDOAImp = courseDOAImp;
        this.addressDOAImp = addressDOAImp;
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
        teacher.setId(id);
        Authenticate authenticate = teacher.getAuth();
        authenticate.setTeacher(teacher);
        Set<Address> address = teacher.getAddress();
        teacher.setAddress(address);
        Teacher saveResponse = teacherDOA.save(teacher);
        String message = String.format("A new teacher have been created with an id %s", saveResponse.getId());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> saveAddress(Long id, Address address, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return InvalidInput.errors(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }
        Optional<Teacher> findTeacher = findById(id);
        if (findTeacher.isEmpty()) {
            String message = String.format("No teacher find with an id %s", id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        String message = String.format("New address have been added for %s.", findTeacher.get().getName());
        address.setTeacher(findTeacher.get());
        return addressDOAImp.save(address, message);
    }

    public ResponseEntity<?> updateAddress(Address address, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return InvalidInput.errors(bindingResult, HttpStatus.NOT_ACCEPTABLE);
        }
        Optional<Address> findAddress = addressDOAImp.findByID(address.getAddressID(), response);
        findAddress.ifPresent(a -> {
            a.setStreet(address.getStreet());
            a.setCity(address.getCity());
            a.setState(address.getState());
            a.setZip(address.getZip());
        });
        String message = String.format("Address have been successfully updated, (ID: %s).", address.getAddressID());
        return addressDOAImp.save(findAddress.get(), message);
    }

    public ResponseEntity<?> removeAddress(Long id, Long aid) {
        Teacher teacher = findByID(id);
        teacherDOA.save(teacher);
        Address address = teacher.findAddress(aid);
        return addressDOAImp.deleteAddress(aid, address);
    }

    public ResponseEntity<?> updateSetCourse(Long id, Long courseID, Course course) {
        Optional<Teacher> findTeacher = findById(id);
        if (course == null) {
            String message = String.format("No course exist with an id %s", courseID);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
        }
        if (findTeacher.isEmpty()) {
            String message = message(id);
            ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
        }
        findTeacher.get().addCourse(course);
        Teacher saveResponse = teacherDOA.save(findTeacher.get());
        String message = String.format(
                "%s course have been added to %s course list",
                course.getName(),
                saveResponse.getName());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> updateRemoveCourse(Long id, Long courseID) {
        Optional<Teacher> findTeacher = findById(id);
        ResponseMessage responseMessage;
        if (findTeacher.isEmpty()) {
            String message = message(id);
            responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        }
        Course course = findTeacher.get().findCourse(courseID);
        findTeacher.get().removeCourse(courseID);
        course.removeTeacher(id);
        courseDOAImp.update(course);
        Teacher saveResponse = teacherDOA.save(findTeacher.get());
        String message = String.format("%s course have been removed from %s course list", course.getName(), saveResponse.getName());
        responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> removeAllCourse(Long id) {
        Optional<Teacher> findTeacher = findById(id);
        if (findTeacher.isEmpty()) {
            ResponseMessage responseMessage = new ResponseMessage(message(id), HttpStatus.NOT_FOUND.name(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
        }
        courseDOAImp.removeTeacher(findTeacher.get());
        findTeacher.get().removeAllCourse();
        Teacher saveResponse = teacherDOA.save(findTeacher.get());
        ResponseMessage responseMessage = new ResponseMessage(
                String.format("All %s course have been deleted", saveResponse.getName()),
                HttpStatus.OK.name(),
                HttpStatus.OK.value()) ;
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public ResponseEntity<?> update(Long id, Teacher teacher) {
        Optional<Teacher> findTeacher = teacherDOA.findById(id);
        String message;

        if (findTeacher.isEmpty()) {
            message = message(id);
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
        message = String.format("Information was successfully updated for teacher with an id %s", updateResult.getId());
        ResponseMessage responseMessage = new ResponseMessage(message, HttpStatus.OK.name(), HttpStatus.OK.value());
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    public List<Teacher> teacherList(HttpServletResponse response) {
        List<Teacher> findAll = teacherDOA.findAllTeacher();
        if (findAll.isEmpty()) {
            Message.noFoundException("Teacher list is empty. Add new teacher", HttpStatus.OK, response);
        }
        return findAll;
    }

    public ResponseEntity<?> deleteTeacher(Long id) {

        Optional<Teacher> findTeacher = teacherDOA.findById(id);
        String message;

        if (findTeacher.isEmpty()) {
            message = message(id);
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
            String message = message(id);
            Message.noFoundException(message, HttpStatus.OK, response);
        }
        return findTeacher;
    }

    private Teacher findByID(Long id) {
        return teacherDOA.getTeacherById(id);
    }

    public Optional<Teacher> findById(Long id) {
        return teacherDOA.findById(id);
    }

    private String message(long id) {
        return String.format("No teacher exist with an id %s", id);
    }
}
