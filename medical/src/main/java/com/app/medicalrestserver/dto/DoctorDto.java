package com.app.medicalrestserver.dto;


import com.app.medicalrestserver.model.Specialization;
import com.app.medicalrestserver.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String login;
    private Specialization specialization;
    private String password;
    private String passwordConfirmation;
    private Set<Visit> visitList;
}
