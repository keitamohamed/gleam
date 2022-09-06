package com.keita.gleam.doa;

import com.keita.gleam.model.Courses;
import org.springframework.data.repository.CrudRepository;

public interface CourseDOA extends CrudRepository<Courses, Long> {
}
