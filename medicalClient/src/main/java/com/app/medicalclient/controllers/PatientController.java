package com.app.medicalclient.controllers;

import com.app.medicalclient.dto.PatientDto;
import com.app.medicalclient.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/all")
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping
    public PatientDto addPatient(@RequestBody PatientDto patientDto){
        return patientService.addPatient(patientDto);
    }

    @GetMapping("/search/{id}")
    public PatientDto getPatientById(@PathVariable Long id){
        return patientService.getPatientById(id);
    }

    // TODO Test delete patient

    @DeleteMapping("/{id}")
    public PatientDto deletePatient(@PathVariable Long id){
        return patientService.deletePatientById(id);
    }
}
