package com.app.medicalclient.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String login;
    private Integer age;
    private String password;
    private String passwordConfirmation;
    private Set<VisitDto> visitList;
}
