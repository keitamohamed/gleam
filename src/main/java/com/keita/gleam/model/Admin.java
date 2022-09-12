package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"adminID"}, allowGetters = true)
public class Admin {
    @Id
    private Long adminID;
    @NotBlank(message = "Enter a valid name")
    private String name;
    @NotNull(message = "Enter a valid date of birth")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date dob;
    @NotBlank(message = "Select a gender")
    private String gender;
    @NotBlank(message = "Enter a valid phone number")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digit number")
    private String phone;


    @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "admin")
    private List<@NotNull(message = "At least one valid address is required") @Valid Address> address;

    @Valid
    @OneToOne(mappedBy = "auth", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "auth")
    private Authenticate auth;

    @OneToMany(mappedBy = "courses", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "courses")
    private Set<Courses> courses;
}
