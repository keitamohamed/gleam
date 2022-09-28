package com.keita.gleam.doa;

import com.keita.gleam.model.Course;
import com.keita.gleam.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseDOA extends CrudRepository<Course, Long> {

    Optional<Course> findByCourseID(Long id);
    @Query(name = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM course c WHERE c.courseName = :name")
    boolean existsByCourseName(@Param("name") String name);
    @NonNull
    List<Course> findAll();
    @Query(value = "SELECT s FROM course c JOIN c.students WHERE c.courseID = :id", nativeQuery = true)
    List<Student> getAllStudentsByCourseID(Long id);
    @Override
    void delete(Course entity);
}
