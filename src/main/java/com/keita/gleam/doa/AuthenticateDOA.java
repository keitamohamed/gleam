package com.keita.gleam.doa;

import com.keita.gleam.model.Authenticate;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthenticateDOA extends CrudRepository<Authenticate, Long> {

    @Query(name = "SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM authenticate a WHERE a.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Authenticate a SET a.password = :password WHERE a.authID = :id")
    void updateAuthPassword(@Param("id") Long id, @Param("password") String password);
}
