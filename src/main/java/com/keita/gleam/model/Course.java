package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course extends Courses{
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "class",
            joinColumns = @JoinColumn(name = "courseID"),
            inverseJoinColumns = @JoinColumn(name = "teacherID")
    )
    @JsonBackReference(value = "tCourses")
    private Set<Teacher> teachers;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "course",
            joinColumns = @JoinColumn(name = "courseID"),
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

}
