package com.app.medicalclient.service;

import com.app.medicalclient.dto.PatientDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientService {

    private RestTemplate restTemplate;

    public PatientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PatientDto> getAllPatients() {
        final String URL = "http://localhost:8080/patient/all";
        List<PatientDto> producers = Arrays.asList(restTemplate.getForObject(URL, PatientDto[].class));
        System.out.println(producers);

        ResponseEntity<PatientDto[]> response = restTemplate.exchange(URL, HttpMethod.GET, null, PatientDto[].class);
        return Arrays.asList(response.getBody());
    }


    public PatientDto getPatientById(Long id){
        final String URL = "http://localhost:8080/patient/search/{id}";

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        ResponseEntity<PatientDto> response = restTemplate.exchange(URL, HttpMethod.GET, null, PatientDto.class, params);
        return response.getBody();
    }
    // TODO Test delete patient

    public PatientDto deletePatientById(Long id){
        final String URL = "http://localhost:8080/patient/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        ResponseEntity<PatientDto> response = restTemplate.exchange(URL, HttpMethod.DELETE, null, PatientDto.class, params);
        return response.getBody();
    }

    public PatientDto addPatient(PatientDto patientDto){
        final String URL = "http://localhost:8080/patient";
        PatientDto productDto1 = PatientDto.builder()
                .name(patientDto.getName())
                .age(patientDto.getAge())
                .login(patientDto.getLogin())
                .surname(patientDto.getSurname())
                .password(patientDto.getPassword())
                .email(patientDto.getEmail())
                .passwordConfirmation(patientDto.getPasswordConfirmation())
                .build();
        HttpEntity<PatientDto> entity = new HttpEntity<>(productDto1);

        ResponseEntity<PatientDto> response = restTemplate.exchange(URL, HttpMethod.POST, entity, PatientDto.class);
        return entity.getBody();
    }
}
