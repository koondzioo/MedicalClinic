package com.app.medicalrestserver.controllerTest;


import com.app.medicalrestserver.controller.PatientController;
import com.app.medicalrestserver.dto.DoctorDto;
import com.app.medicalrestserver.dto.PatientDto;
import com.app.medicalrestserver.service.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PatientController.class)
public class PatientControllerTests {

    @MockBean
    private PatientService patientService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllPatientsTest() throws Exception {

        var patient1 = PatientDto.builder()
                .id(1L)
                .name("Adam")
                .surname("Nowak")
                .login("Login1")
                .age(23)
                .email("email@email.com")
                .build();

        var patient2 = PatientDto.builder()
                .id(1L)
                .name("Jan")
                .surname("Kowalski")
                .login("Login2")
                .age(45)
                .email("email1@email.com")
                .build();

        var patients = List.of(patient1, patient2);

        Mockito
                .when(patientService.getAllPatients())
                .thenReturn(patients);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/patient/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(patient1.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is(patient2.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].age", Matchers.is(patient2.getAge())));

    }


    @Test
    public void getDoctorTest() throws Exception {

        var patient = PatientDto.builder()
                .id(1L)
                .name("Adam")
                .surname("Nowak")
                .login("Login1")
                .age(23)
                .email("email@email.com")
                .build();

        Mockito.when(patientService.findPatientById(1L))
                .thenReturn(patient);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/patient/search/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(patient.getName())));
    }

    @Test
    public void addDoctorTest() throws Exception {

        var patient = PatientDto.builder()
                .name("Adam")
                .surname("Nowak")
                .login("Login1")
                .age(23)
                .email("email@email.com")
                .build();

        var patientFromDB = PatientDto.builder()
                .id(1L)
                .name("Adam")
                .surname("Nowak")
                .login("Login1")
                .age(23)
                .email("email@email.com")
                .build();

        Mockito
                .when(patientService.addPatient(patient))
                .thenReturn(patientFromDB);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/patient")
                        .content(toJson(patient))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(patient.getName())));

    }

    private static String toJson(PatientDto patient) {
        try {
            return new ObjectMapper().writeValueAsString(patient);
        } catch (Exception e) {
            throw new IllegalStateException("json exception");
        }
    }



}
