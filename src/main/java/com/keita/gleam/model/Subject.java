package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    private Long subjectID;
    @NotBlank(message = "Subject name is required")
    private String name;
    @NotBlank(message = "Subject description is required")
    @Lob
    private String description;

    @ManyToMany(mappedBy = "subjects", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Major> major;

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "subject")
    private Set<Course> course;
}
