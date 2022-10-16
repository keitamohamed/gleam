package com.keita.gleam.doa;

import com.keita.gleam.model.Teacher;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherDOA extends CrudRepository<Teacher, Long> {

    @NonNull
    Teacher save(Teacher teacher);

    @Override
    Optional<Teacher> findById(Long aLong);
    @Query(value = "SELECT * " +
            "FROM Teacher AS t " +
            "WHERE t.id = :id", nativeQuery = true)
    Teacher getTeacherById(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT * FROM Teacher ", nativeQuery = true)
    List<Teacher>findAllTeacher();

    @Override
    void delete(Teacher entity);
}
