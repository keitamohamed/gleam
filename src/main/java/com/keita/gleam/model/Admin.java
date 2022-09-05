package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Admin {
    @Id
    private Long adminID;
    private String name;

    @OneToMany(mappedBy = "address", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "address")
    private List<Address> address;
}
