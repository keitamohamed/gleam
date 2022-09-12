package com.keita.gleam.doa;

import com.keita.gleam.model.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface StudentDOA extends CrudRepository<Student, Long> {

    @NonNull
    Student save(Student student);
    @Override
    Optional<Student> findById(Long aLong);
    @NonNull
    List<Student> findAll();
    @Override
    void delete(Student entity);
}
