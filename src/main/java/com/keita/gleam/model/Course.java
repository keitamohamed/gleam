package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Course extends Courses {
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Teacher> teachers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "course",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "studentID")
    )
    @JsonBackReference(value = "courses")
    private Set<Student> students;

    public void AddNewStudent(Student student) {
        newStudent(student);
    }
    private void newStudent(Student student) {
        students.add(student);
    }

    public void addTeacher(Teacher teacher) {
        addNewTeacher(teacher);
    }
    private void addNewTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public boolean removeTeacher(Long id) {
        return remove(id);
    }

    private boolean remove(Long id) {
        return this.teachers.removeIf(teacher -> Objects.equals(teacher.getId(), id));
    }

    public boolean removeTeacher(Teacher teacher) {
        return (this.teachers.removeIf(t -> Objects.equals(t.getId(), teacher.getId())));
    }

}
