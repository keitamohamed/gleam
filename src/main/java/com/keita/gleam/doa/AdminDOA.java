package com.keita.gleam.doa;

import com.keita.gleam.model.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface AdminDOA extends CrudRepository<Admin, Long> {

    @NonNull
     Admin save(Admin admin);
    Optional<Admin> getAdminByAdminID(Long id);
    @NonNull
    List<Admin> findAll();
    @Override
    @NonNull
    void delete(Admin entity);
}
