package com.app.medicalrestserver.controller;

import com.app.medicalrestserver.dto.PatientDto;
import com.app.medicalrestserver.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public PatientDto addPatient(@RequestBody PatientDto patientDto){
        return patientService.addPatient(patientDto);
    }

    @GetMapping("/all")
    public List<PatientDto> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("search/{id}")
    public PatientDto getPatientById(@PathVariable Long id) {
        return patientService.findPatientById(id);
    }

    @DeleteMapping("/{id}")
    public PatientDto deletePatient(@PathVariable Long id){
        return patientService.deletePatient(id);
    }


}
