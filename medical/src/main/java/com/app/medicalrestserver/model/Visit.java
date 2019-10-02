package com.app.medicalrestserver.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Visit {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "doctor_id" )
    private Doctor doctor;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id" )
    private Patient patient;
    private String description;
}
