package com.app.medicalrestserver.controllerTest;

import com.app.medicalrestserver.controller.DoctorController;
import com.app.medicalrestserver.dto.DoctorDto;
import com.app.medicalrestserver.model.Specialization;
import com.app.medicalrestserver.service.DoctorService;
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
@WebMvcTest(DoctorController.class)
public class DoctorControllerTest {

    @MockBean
    private DoctorService doctorService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllDoctorsTest() throws Exception {

        var doc1 = DoctorDto.builder()
                .id(1L)
                .name("Doctor1")
                .surname("Doctor1")
                .specialization(Specialization.IMMUNOLOGY)
                .login("Login1")
                .email("email@email.com")
                .build();

        var doc2 = DoctorDto.builder()
                .id(2L)
                .name("Doctor2")
                .surname("Doctor2")
                .specialization(Specialization.NEUROLOGY)
                .login("Login2")
                .email("email2@email.com")
                .build();

        var doctors = List.of(doc1, doc2);

        Mockito
                .when(doctorService.getAllDoctors())
                .thenReturn(doctors);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/doctor/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(doc1.getName())));

    }

    @Test
    public void getDoctorsBySpecializationTest() throws Exception {

        var doc1 = DoctorDto.builder()
                .id(1L)
                .name("Doctor1")
                .surname("Doctor1")
                .specialization(Specialization.IMMUNOLOGY)
                .login("Login1")
                .email("email@email.com")
                .build();

        var doc2 = DoctorDto.builder()
                .id(2L)
                .name("Doctor2")
                .surname("Doctor2")
                .specialization(Specialization.NEUROLOGY)
                .login("Login2")
                .email("email2@email.com")
                .build();

        var doc3 = DoctorDto.builder()
                .id(3L)
                .name("Doctor3")
                .surname("Doctor3")
                .specialization(Specialization.NEUROLOGY)
                .login("Login3")
                .email("email3@email.com")
                .build();

        var doctors = List.of(doc1, doc2, doc3);
        var doctorsNeurology = List.of(doc2, doc3);

        Mockito
                .when(doctorService.getDoctorsBySpecialization(Specialization.NEUROLOGY))
                .thenReturn(doctorsNeurology);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/doctor/{specialization}", Specialization.NEUROLOGY)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is(doc2.getName())));

    }

    @Test
    public void getDoctorTest() throws Exception {

        var doc = DoctorDto.builder()
                .id(1L)
                .name("Doctor1")
                .surname("Doctor1")
                .specialization(Specialization.IMMUNOLOGY)
                .login("Login1")
                .email("email@email.com")
                .build();

        Mockito.when(doctorService.findDoctorById(1L))
                .thenReturn(doc);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/doctor/search/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(doc.getName())));
    }

    @Test
    public void addDoctorTest() throws Exception {

        var doc = DoctorDto.builder()
                .name("Doctor1")
                .surname("Doctor1")
                .specialization(Specialization.IMMUNOLOGY)
                .login("Login1")
                .email("email@email.com")
                .build();

        var docFromDB = DoctorDto.builder()
                .id(1L)
                .name("Doctor1")
                .surname("Doctor1")
                .specialization(Specialization.IMMUNOLOGY)
                .login("Login1")
                .email("email@email.com")
                .build();

        Mockito
                .when(doctorService.addDoctor(doc))
                .thenReturn(docFromDB);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/doctor")
                        .content(toJson(doc))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is(doc.getName())));

    }


    private static String toJson(DoctorDto doctor) {
        try {
            return new ObjectMapper().writeValueAsString(doctor);
        } catch (Exception e) {
            throw new IllegalStateException("json exception");
        }
    }

}
