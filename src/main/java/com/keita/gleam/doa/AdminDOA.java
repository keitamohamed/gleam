package com.keita.gleam.doa;

import com.keita.gleam.model.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminDOA extends CrudRepository<Admin, Long> {
     Admin save(Admin admin);
    Admin getAdminByAdminID(Long id);
    void deleteAdminByAdminID(Long id);
}
