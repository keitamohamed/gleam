package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Major implements Serializable {

    @Id
    private Long majorID;
    @Column(unique = true, updatable = false)
    @NotBlank(message = "Enter a valid major name")
    private String name;

    @NotBlank(message = "Enter a valid major description")
    @Size(min = 100, message = "Description must be at least 100 world")
    @Lob
    private String description;

    @ManyToMany(mappedBy = "majors", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference(value = "students")
    private Set<Student> students;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "major_subject",
            joinColumns = @JoinColumn(name = "major_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjects;
    public boolean addNewSubject(Subject subject) {
        return addSubject(subject);
    }
    private boolean addSubject(Subject subject) {
        return subjects.add(subject);
    }

    public boolean removeAllSubject(Set<Subject> subjects) {
        return (this.getSubjects().removeAll(subjects));
    }
}
