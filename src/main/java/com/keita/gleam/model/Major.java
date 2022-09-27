package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Major {

    @Id
    private Long majorID;
    @NotBlank(message = "Enter a valid major name")
    private String name;
    @NotBlank(message = "Enter a valid major description")
    @Size(min = 100, message = "Description must be at least 100 world")
    @Lob
    private String description;
    @OneToMany(mappedBy = "major", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "major")
    private Set<Subject> subjects;
}
