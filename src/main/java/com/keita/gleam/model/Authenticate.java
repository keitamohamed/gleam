package com.keita.gleam.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Authenticate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authID;

    @NotBlank(message = "Email address cannot be blank")
    @Email(message = "Email is not a valid email address.",
            regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    @Column(updatable = false, unique = true)
    private String email;
    @NotBlank(message = "Enter a valid password")
    @Lob
    private String password;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "adminID")
    @JsonBackReference(value = "admin")
    private Admin admin;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacherID")
    @JsonBackReference(value = "teacher")
    private Teacher teacher;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "studentID")
    @JsonBackReference(value = "student")
    private Student student;
}
