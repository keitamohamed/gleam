package com.keita.gleam.doa;

import com.keita.gleam.model.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
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
