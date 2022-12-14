package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Serializable {

    @Id
    private Long subjectID;
    @NotBlank(message = "Subject name is required")
    private String name;
    @NotBlank(message = "Subject description is required")
    @Lob
    private String description;

    @ManyToMany(mappedBy = "subjects", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference(value = "major")
    private Set<Major> major;

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "subject")
    private Set<Course> courses;

    public void addNewMajor(Major major) {
        add(major);
    }

    private void add(Major m) {
        major.add(m);
    }

    public boolean removeMajors(Set<Major> major) {
        return (this.major.removeAll(major));
    }

    public boolean removeCourse(Long id) {
        return (this.courses.removeIf(c -> Objects.equals(c.getId(), id)));
    }
}
