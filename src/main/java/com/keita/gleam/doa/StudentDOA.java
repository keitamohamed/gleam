package com.keita.gleam.doa;

import com.keita.gleam.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentDOA extends CrudRepository<Student, Long> {
}
