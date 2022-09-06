package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminID;
    @NotBlank(message = "Enter a valid name")
    private String name;
    @NotNull(message = "Enter a valid date of birth")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dob;
    @NotBlank(message = "Select a gender")
    private String gender;
    @NotBlank(message = "Enter a valid phone number")
    private String phone;

    @OneToMany(mappedBy = "aAddress", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "aAddress")
    private List<Address> aAddress;

    @OneToOne(mappedBy = "adminAuth", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "adminAuth")
    private Authenticate adminAuth;

    @OneToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "courses")
    private Set<Courses> courses;
}
