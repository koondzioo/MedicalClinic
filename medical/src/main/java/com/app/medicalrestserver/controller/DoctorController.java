package com.app.medicalrestserver.controller;


import com.app.medicalrestserver.dto.DoctorDto;
import com.app.medicalrestserver.model.Specialization;
import com.app.medicalrestserver.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    private DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public DoctorDto addDoctor(@RequestBody DoctorDto doctorDto) {
        return doctorService.addDoctor(doctorDto);
    }

    @GetMapping("/all")
    public List<DoctorDto> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @DeleteMapping("/{id}")
    public DoctorDto deleteDoctor(@PathVariable Long id) {
        return doctorService.deleteDoctor(id);
    }

    @GetMapping("/search/{id}")
    public DoctorDto getDoctorById(@PathVariable Long id) {
        return doctorService.findDoctorById(id);
    }

    @GetMapping("/{specialization}")
    public List<DoctorDto> getDoctorsBySpecialization(@PathVariable Specialization specialization) {
        return doctorService.getDoctorsBySpecialization(specialization);
    }
}
