package com.keita.gleam.doa;

import com.keita.gleam.model.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubjectDOA extends CrudRepository<Subject, Long> {

    @Query(name = "SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM subject s WHERE s.subjectID = :id")
    boolean existsBySubjectID(@Param("id") Long id);
    @Query(name = "SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM subject s WHERE s.name = :name")
    boolean existsByName(@Param("name") String name);

    @Transactional
    List<Subject> findAll();
}
