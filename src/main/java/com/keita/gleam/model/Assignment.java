package com.keita.gleam.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    private Long id;
    @NotBlank(message = "Enter a valid assignment name")
    private String name;
    @NotBlank(message = "Enter a valid description")
    private String description;
    @Size(min = 5, max = 100, message = "Enter a valid point")
    private int point;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "courseID")
    @JsonBackReference(value = "assignment")
    private Courses assignment;

}
