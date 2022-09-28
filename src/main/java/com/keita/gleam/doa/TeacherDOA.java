package com.keita.gleam.doa;

import com.keita.gleam.model.Teacher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherDOA extends CrudRepository<Teacher, Long> {

    @NonNull
    Teacher save(Teacher teacher);
    @Override
    Optional<Teacher> findById(Long aLong);
    Teacher getTeacherByTeacherID(Long id);
    @NonNull
    List<Teacher>findAll();
    @Override
    void delete(Teacher entity);
}
