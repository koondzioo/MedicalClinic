package com.app.medicalrestserver.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitDto {

    private Long id;
    private LocalDate date;
    private Long doctorId;
    private Long patientId;
    private String description;
}
