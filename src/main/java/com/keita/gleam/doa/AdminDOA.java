package com.keita.gleam.doa;

import com.keita.gleam.model.Admin;

public interface AdminDOA {

    void save(Admin admin);
    Admin update(Admin admin);
    Admin findAdmin(Long id);
    void deleteAdmin(Long id);
}
