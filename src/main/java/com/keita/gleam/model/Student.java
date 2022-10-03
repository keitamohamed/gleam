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
import java.util.Objects;
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
    @OneToOne(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "student")
    private Authenticate auth;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "student")
    private Set<@NotNull(message = "At least one valid address is required") @Valid Address> address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "study",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "major_id")
    )
    private Set<Major> majors;

    @ManyToMany(mappedBy = "students", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Course> courses;

    public void addNewCourse(Course courses) {
        addCourse(courses);
    }

    private void addCourse(Course newCourse) {
        courses.add(newCourse);
    }

    public boolean addMajor(Major major) {
        return (this.majors.add(major));
    }

    public boolean removeMajor(Long id) {
        return (this.majors.removeIf(m -> Objects.equals(m.getMajorID(), id)));
    }
}
