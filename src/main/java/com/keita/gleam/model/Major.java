package com.keita.gleam.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Major {

    @Id
    private Long majorID;
    private String name;
    private String description;

    @OneToMany(mappedBy = "major", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference(value = "major")
    private Set<Subject> subjects;
}
