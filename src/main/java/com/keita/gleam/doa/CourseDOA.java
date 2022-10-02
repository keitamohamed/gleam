package com.keita.gleam.doa;

import com.keita.gleam.model.Course;
import com.keita.gleam.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseDOA extends CrudRepository<Course, Long> {

    Optional<Course> findById(Long id);
    @Query(name = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM course c WHERE c.courseName = :name")
    boolean existsByName(@Param("name") String name);

    @Transactional
    @Query(value = "SELECT * FROM courses", nativeQuery = true)
    List<Course> getCourses();
    @Query(value = "SELECT s FROM course c JOIN c.students WHERE c.id = :id", nativeQuery = true)
    List<Student> getAllStudentsByID(Long id);
    @Override
    void delete(Course entity);
}
