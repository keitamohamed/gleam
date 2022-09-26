package com.keita.gleam.doa;

import com.keita.gleam.model.Major;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorDOA extends CrudRepository<Major, Long> {
    @Query(name = "SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM major m WHERE c.name = :name")
    boolean existsByName(@Param("name") String name);

}
