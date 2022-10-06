package com.keita.gleam.model;


import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "teacherID"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"teacherID"}, allowGetters = true)
public class Teacher {

    @Id
    private Long teacherID;
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
    @OneToOne(mappedBy = "teacher", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "teacher")
    private Authenticate auth;

    @OneToMany(mappedBy = "teacher", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "teacher")
    private Set<@NotNull(message = "At least one valid address is required") @Valid Address> address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Course> courses;

    public void addCourse(Course course) {
        addNewCourse(course);
    }
    private void addNewCourse(Course course) {
        courses.add(course);
    }

    public boolean removeCourse(Long courseID) {
        return remove(courseID);
    }

    private boolean remove(Long courseID) {
        return this.courses.removeIf(course -> Objects.equals(course.getId(), courseID));
    }

    public Course findCourse(Long id) {
        return this.courses
                .stream()
                .filter(course -> Objects.equals(course.getId(), id))
                .findAny()
                .orElse(null);
    }
}
