package com.app.medicalrestserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Doctor {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String login;
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    private String password;
    @Transient
    private String passwordConfirmation;
    @OneToMany(mappedBy = "doctor")
    @ToString.Exclude
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Set<Visit> visitList = new LinkedHashSet<>();

}
