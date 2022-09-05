package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Address {

    @Id
    private Long id;
    private String street;
    private String city;
    private String state;
    private int zip;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "adminID")
    @JsonBackReference(value = "address")
    private Admin address;
}
