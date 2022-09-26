package com.keita.gleam.service;

import com.keita.gleam.doa.AuthenticateDOA;
import com.keita.gleam.model.Authenticate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateDOAImp {

    private final AuthenticateDOA authenticateDOA;

    @Autowired
    public AuthenticateDOAImp(AuthenticateDOA authenticateDOA) {
        this.authenticateDOA = authenticateDOA;
    }

    public boolean emailExist(String email) {
        return authenticateDOA.existsByEmail(email);
    }

    public ResponseEntity<?> updatePassword(Long id, Authenticate authenticate) {
        System.out.println(authenticate.getPassword());
        authenticateDOA.updateAuthPassword(id, authenticate.getPassword());
        return new ResponseEntity<>("Password have been update successfully.", HttpStatus.OK);
    }
}
