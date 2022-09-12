package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"studentID"}, allowGetters = true)
public class Student {

    @Id
    private Long studentID;
    @NotBlank(message = "Enter a valid name")
    private String name;
    @NotNull(message = "Enter a valid date of birth")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dob;
    @NotBlank(message = "Select a gender")
    private String gender;
    @NotBlank(message = "Enter a valid phone number")
    private String phone;

    @Valid
    @OneToOne(mappedBy = "sAuth", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "sAuth")
    private Authenticate sAuth;

    @Valid
    @OneToMany(mappedBy = "sAddress", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "sAddress")
    private List<Address> sAddress;

    @OneToMany(mappedBy = "classes", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "classes")
    private List<Courses> classes;
}
