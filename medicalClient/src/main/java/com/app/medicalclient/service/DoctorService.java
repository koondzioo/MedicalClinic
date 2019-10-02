package com.app.medicalclient.service;

import com.app.medicalclient.dto.DoctorDto;
import com.app.medicalclient.dto.Specialization;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorService {

    private RestTemplate restTemplate;

    public DoctorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DoctorDto> getAllDoctors() {
        final String URL = "http://localhost:8080/doctor/all";
        List<DoctorDto> producers = Arrays.asList(restTemplate.getForObject(URL, DoctorDto[].class));
        System.out.println(producers);

        ResponseEntity<DoctorDto[]> response = restTemplate.exchange(URL, HttpMethod.GET, null, DoctorDto[].class);
        return Arrays.asList(response.getBody());
    }


    public DoctorDto getDoctorById(Long id){
        final String URL = "http://localhost:8080/doctor/search/{id}";

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        ResponseEntity<DoctorDto> response = restTemplate.exchange(URL, HttpMethod.GET, null, DoctorDto.class, params);
        return response.getBody();
    }
    // TODO Test delete patient

    public DoctorDto deleteDoctorById(Long id){
        final String URL = "http://localhost:8080/doctor/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        ResponseEntity<DoctorDto> response = restTemplate.exchange(URL, HttpMethod.DELETE, null, DoctorDto.class, params);
        return response.getBody();
    }

    public DoctorDto addDoctor(DoctorDto doctorDto){
        final String URL = "http://localhost:8080/doctor";
        DoctorDto doctorDto1 = DoctorDto.builder()
                .name(doctorDto.getName())
                .specialization(doctorDto.getSpecialization())
                .login(doctorDto.getLogin())
                .surname(doctorDto.getSurname())
                .password(doctorDto.getPassword())
                .email(doctorDto.getEmail())
                .passwordConfirmation(doctorDto.getPasswordConfirmation())
                .build();
        HttpEntity<DoctorDto> entity = new HttpEntity<>(doctorDto1);

        ResponseEntity<DoctorDto> response = restTemplate.exchange(URL, HttpMethod.POST, entity, DoctorDto.class);
        return entity.getBody();
    }

    public List<DoctorDto> getDoctorsBySpecialization(String specialization) {
        final String URL = "http://localhost:8080/doctor/{specialization}";
        Map<String, String> params = new HashMap<>();
        params.put("specialization", specialization);
        ResponseEntity<DoctorDto[]> response = restTemplate.exchange(URL, HttpMethod.GET, null, DoctorDto[].class, params);
        return Arrays.asList(response.getBody());
    }
}
