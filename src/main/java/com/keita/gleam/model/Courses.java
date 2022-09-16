package com.keita.gleam.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Courses {

    @Id
    private Long courseID;
    @NotBlank(message = "Course name is required")
    private String courseName;
    @NotBlank(message = "Course description is required")
    private String description;
    @Positive(message = "Course credit must be a positive number")
    @Min(value = 3, message = "Course credit must 3 credit and over")
    @Max(value = 4, message = "Course credit must be 3 to 4 credit")
    private int credit;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "adminID")
    @JsonBackReference(value = "admin")
    private Admin admin;

    @OneToMany(mappedBy = "assignment", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference(value = "assignment")
    private List<Assignment> assignment;

}
