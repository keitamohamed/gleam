package com.keita.gleam.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Column(updatable = false, unique = true)
    @NotBlank(message = "Enter valid email address")
    private String email;
    @Column(columnDefinition = "LONGBLOB")
    @NotBlank(message = "Enter a valid password")
    private String password;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "adminID")
    @JsonBackReference(value = "adminAuth")
    private Admin adminAuth;

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacherID")
    @JsonBackReference(value = "teacherAuth")
    private Teacher teacherAuth;

    @OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "studentID")
    @JsonBackReference(value = "studentAuth")
    private Student studentAuth;
}
