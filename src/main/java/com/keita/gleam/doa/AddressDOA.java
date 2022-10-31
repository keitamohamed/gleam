package com.keita.gleam.doa;

import com.keita.gleam.model.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AddressDOA extends CrudRepository<Address, Long> {
    @NonNull
    Optional<Address> findAddressByAddressID(@Param("id") Long id);

    @Transactional
    @Query(value = "SELECT * FROM Address as a WHERE a.addressID = :id", nativeQuery = true)
    Address findAddressByID(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Address as a WHERE a.addressID = :id", nativeQuery = true)
    public void deleteByAddressID(@Param("id") Long id);
}
