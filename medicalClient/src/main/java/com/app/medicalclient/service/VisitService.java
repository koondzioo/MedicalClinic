package com.app.medicalclient.service;

import com.app.medicalclient.dto.DoctorDto;
import com.app.medicalclient.dto.VisitDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VisitService {

    private RestTemplate restTemplate;

    public VisitService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public VisitDto addVisit(VisitDto visitDto) {
        final String URL = "http://localhost:8080/visit";
        VisitDto visitDto1 = VisitDto.builder()
                .date(visitDto.getDate())
                .description(visitDto.getDescription())
                .doctorId(visitDto.getDoctorId())
                .patientId(visitDto.getPatientId())
                .build();
        HttpEntity<VisitDto> entity = new HttpEntity<>(visitDto1);

        ResponseEntity<VisitDto> response = restTemplate.exchange(URL, HttpMethod.POST, entity, VisitDto.class);
        return entity.getBody();
    }

    public List<VisitDto> getVisitsByPatient(Long id) {
        final String URL = "http://localhost:8080/visit/byPatient/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        ResponseEntity<VisitDto[]> response = restTemplate.exchange(URL, HttpMethod.GET, null, VisitDto[].class, params);
        return Arrays.asList(response.getBody());
    }

    public VisitDto getVisitById(Long id) {
        final String URL = "http://localhost:8080/visit/{id}";

        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        ResponseEntity<VisitDto> response = restTemplate.exchange(URL, HttpMethod.GET, null, VisitDto.class, params);
        return response.getBody();
    }


    public VisitDto deleteVisit(Long id) {
        final String URL = "http://localhost:8080/visit/{id}";
        Map<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));
        ResponseEntity<VisitDto> response = restTemplate.exchange(URL, HttpMethod.DELETE, null, VisitDto.class, params);
        return response.getBody();
    }
}
