package com.app.medicalclient.controllers;

import com.app.medicalclient.dto.DoctorDto;
import com.app.medicalclient.dto.Specialization;
import com.app.medicalclient.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/all")
    public List<DoctorDto> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PostMapping
    public DoctorDto addDoctor(@RequestBody DoctorDto doctorDto){
        return doctorService.addDoctor(doctorDto);
    }

    @GetMapping("/search/{id}")
    public DoctorDto getDoctorById(@PathVariable Long id){
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/{specialization}")
    public List<DoctorDto> getDoctorsBySpecialization(@PathVariable String specialization){
        return doctorService.getDoctorsBySpecialization(specialization);
    }

    // TODO Test delete doctor

    @DeleteMapping("/{id}")
    public DoctorDto deleteDoctor(@PathVariable Long id){
        return doctorService.deleteDoctorById(id);
    }
}
