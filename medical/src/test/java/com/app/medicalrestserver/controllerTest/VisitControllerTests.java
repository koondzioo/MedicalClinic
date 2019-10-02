package com.app.medicalrestserver.controllerTest;

import com.app.medicalrestserver.controller.VisitController;
import com.app.medicalrestserver.dto.DoctorDto;
import com.app.medicalrestserver.dto.PatientDto;
import com.app.medicalrestserver.dto.VisitDto;
import com.app.medicalrestserver.model.Specialization;
import com.app.medicalrestserver.service.VisitService;
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

import javax.print.attribute.standard.Media;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VisitController.class)
public class VisitControllerTests {

    @MockBean
    private VisitService visitService;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getVisitByIdTest() throws Exception {
        var patient = PatientDto.builder()
                .id(1L)
                .name("Patient1")
                .surname("Patient1")
                .login("Patient1")
                .age(23)
                .email("email@email.com")
                .build();

        var doctor = DoctorDto.builder()
                .id(2L)
                .name("Doctor1")
                .surname("Doctor1")
                .specialization(Specialization.IMMUNOLOGY)
                .login("Doctor1")
                .email("email1@email.com")
                .build();

        var visit = VisitDto.builder()
                .id(3L)
                .description("Visit")
                .date(LocalDate.of(2019,10,12))
                .patientId(patient.getId())
                .doctorId(doctor.getId())
                .build();


        Mockito
                .when(visitService.getVisitById(3L))
                .thenReturn(visit);

        mockMvc
                .perform(MockMvcRequestBuilders
                    .get("/visit/{id}", 3L)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(visit.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorId", Matchers.is(visit.getDoctorId().intValue())));
    }


    @Test
    public void getVisitByPatient() throws Exception {

        var patient = PatientDto.builder()
                .id(1L)
                .name("Patient1")
                .surname("Patient1")
                .login("Patient1")
                .age(23)
                .email("email@email.com")
                .build();

        var doctor1 = DoctorDto.builder()
                .id(2L)
                .name("Doctor1")
                .surname("Doctor1")
                .specialization(Specialization.IMMUNOLOGY)
                .login("Doctor1")
                .email("email1@email.com")
                .build();

        var doctor2 = DoctorDto.builder()
                .id(3L)
                .name("Doctor2")
                .surname("Doctor2")
                .specialization(Specialization.NEUROLOGY)
                .login("Doctor2")
                .email("email2@email.com")
                .build();

        var visit1 = VisitDto.builder()
                .id(4L)
                .description("Visit 1")
                .date(LocalDate.of(2019,10,22))
                .patientId(patient.getId())
                .doctorId(doctor1.getId())
                .build();

        var visit2 = VisitDto.builder()
                .id(5L)
                .description("Visit 2")
                .date(LocalDate.of(2019,11,12))
                .patientId(patient.getId())
                .doctorId(doctor2.getId())
                .build();


        var visits = List.of(visit1, visit2);

        Mockito
                .when(visitService.getVisitsByPatient(1L))
                .thenReturn(visits);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/visit/byPatient/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description", Matchers.is(visit1.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].description", Matchers.is(visit2.getDescription())));
    }

    // TODO : CHANGE FORMATTING JAVA TIME  --> jackson-datatype
/*
    @Test
    public void addVisitTest() throws Exception {
        var patient = PatientDto.builder()
                .id(1L)
                .name("Patient1")
                .surname("Patient1")
                .login("Patient1")
                .age(23)
                .email("email@email.com")
                .build();

        var doctor = DoctorDto.builder()
                .id(2L)
                .name("Doctor1")
                .surname("Doctor1")
                .specialization(Specialization.IMMUNOLOGY)
                .login("Doctor1")
                .email("email1@email.com")
                .build();

        var visit = VisitDto.builder()
                .description("Visit")
                .date(LocalDate.of(2019,10,12))
                .patientId(patient.getId())
                .doctorId(doctor.getId())
                .build();

        var visitFromDB = VisitDto.builder()
                .id(3L)
                .description("Visit")
                .date(LocalDate.of(2019,10,12))
                //.patientId(patient.getId())
                //.doctorId(doctor.getId())
                .build();


        Mockito
                .when(visitService.addVisit(visit))
                .thenReturn(visitFromDB);

        System.out.println(toJson(visit));

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/visit")
                        .content(toJson(visit))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is(visit.getDescription())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorId", Matchers.is(visit.getDoctorId().intValue())));
    }

    private static String toJson(VisitDto visit) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            VisitDto visitDto = visit;
            LocalDate date = df.parse(visit.getDate())
            return new ObjectMapper().setDateFormat(df).writeValueAsString(visit);
        } catch (Exception e) {
            throw new IllegalStateException("json exception");
        }
    }
   */


}
