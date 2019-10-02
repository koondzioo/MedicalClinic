package com.app.medicalrestserver.serviceTest;

import com.app.medicalrestserver.dto.mappers.ModelMapper;
import com.app.medicalrestserver.model.Patient;
import com.app.medicalrestserver.repository.PatientRepository;
import com.app.medicalrestserver.service.PatientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class PatientServiceTest {

    @TestConfiguration
    public static class PatientServiceConfiguration {

        @MockBean
        public PatientRepository patientRepository;

        @Bean
        public PatientService patientService() {
            return new PatientService(patientRepository);
        }

    }

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientRepository patientRepository;


    @Test
    public void getAllDoctorsTest() {

        Mockito
                .when(patientRepository.findAll())
                .thenReturn(List.of(
                        Patient.builder()
                                .id(1L)
                                .name("Adam")
                                .surname("Nowak")
                                .login("Login1")
                                .age(23)
                                .email("email@email.com")
                                .build(),
                        Patient.builder()
                                .id(2L)
                                .name("Jan")
                                .surname("Kowalski")
                                .login("Login2")
                                .age(23)
                                .email("email2@email.com")
                                .build()
                ));

        var patients = patientService.getAllPatients();

        Assertions.assertEquals(2, patients.size());

    }

    @Test
    public void getPatientByIdTest() {

        var patientFromRep = Patient.builder()
                .id(1L)
                .name("Adam")
                .surname("Nowak")
                .login("Login1")
                .age(23)
                .email("email@email.com")
                .build();

        Mockito
                .when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patientFromRep));

        var doctor = patientService.findPatientById(1L);
        Assertions.assertEquals("Adam", doctor.getName());
        Assertions.assertEquals("Nowak", doctor.getSurname());
        Assertions.assertEquals("Login1", doctor.getLogin());
    }



    @Test
    public void addPatientTest() {

        var patient = Patient.builder()
                .id(1L)
                .name("Adam")
                .surname("Nowak")
                .login("Login1")
                .age(23)
                .email("email@email.com")
                .build();

        Mockito
                .when(patientRepository.save(patient))
                .thenReturn(patient);

        var patientFromService = patientService.addPatient(ModelMapper.fromPatientToPatientDto(patient));
        Assertions.assertEquals(patient.getName(),patientFromService.getName());
        Assertions.assertEquals(patient.getEmail(),patientFromService.getEmail());
        Assertions.assertEquals(patient.getSurname(),patientFromService.getSurname());

    }

}
