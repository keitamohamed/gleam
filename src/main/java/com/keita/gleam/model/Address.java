package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressID;
    @NotBlank(message = "Enter street address")
    private String street;
    @NotBlank(message = "Enter a valid city")
    private String city;
    @NotBlank(message = "Enter a valid state")
    private String state;
    @Min(value = 4, message = "Zip code must be 5 digit number")
    @Max(value = 94957, message = "Zip code cannot be more then 5 digit number")
    private int zip;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "adminID")
    @JsonBackReference(value = "admin")
    private Admin admin;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacherID")
    @JsonBackReference(value = "teacher")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "studentID")
    @JsonBackReference(value = "sAddress")
    private Student sAddress;
}
