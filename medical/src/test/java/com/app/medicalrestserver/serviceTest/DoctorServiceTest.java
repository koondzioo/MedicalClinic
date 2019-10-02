package com.app.medicalrestserver.serviceTest;

import com.app.medicalrestserver.dto.mappers.ModelMapper;
import com.app.medicalrestserver.model.Doctor;
import com.app.medicalrestserver.model.Specialization;
import com.app.medicalrestserver.repository.DoctorRepository;
import com.app.medicalrestserver.service.DoctorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class DoctorServiceTest {

    @TestConfiguration
    public static class DoctorServiceConfiguration {

        @MockBean
        public DoctorRepository doctorRepository;

        @Bean
        public DoctorService doctorService() {
            return new DoctorService(doctorRepository);
        }

    }

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void getAllDoctorsTest() {

        Mockito
                .when(doctorRepository.findAll())
                .thenReturn(List.of(
                        Doctor.builder()
                                .login("Doctor1")
                                .name("Doctor1")
                                .surname("Doctor1")
                                .email("Email1@email.com")
                                .password("123")
                                .specialization(Specialization.NEUROLOGY)
                                .build(),
                        Doctor.builder()
                                .login("Doctor2")
                                .name("Doctor2")
                                .surname("Doctor2")
                                .email("Email2@email.com")
                                .password("123")
                                .specialization(Specialization.ANESTHESIOLOGY)
                                .build()
                ));

        var doctors = doctorService.getAllDoctors();

        Assertions.assertEquals(2, doctors.size());

    }

    @Test
    public void getDoctorByIdTest() {

        var doctorFromRepo = Doctor.builder()
                .id(1L)
                .login("Doctor1")
                .name("Doctor1")
                .surname("Doctor1")
                .email("Email1@email.com")
                .password("123")
                .passwordConfirmation("123")
                .visitList(new HashSet<>())
                .specialization(Specialization.NEUROLOGY)
                .build();

        Mockito
                .when(doctorRepository.findById(1L))
                .thenReturn(Optional.of(doctorFromRepo));

        var doctor = doctorService.findDoctorById(1L);
        Assertions.assertEquals("Doctor1", doctor.getName());
        Assertions.assertEquals("Doctor1", doctor.getSurname());
        Assertions.assertEquals("Doctor1", doctor.getLogin());
    }


    @Test
    public void getDoctorsBySpecializationTest() {

        Mockito
                .when(doctorRepository.findAll())
                .thenReturn(List.of(
                        Doctor.builder()
                                .login("Doctor1")
                                .name("Doctor1")
                                .surname("Doctor1")
                                .email("Email1@email.com")
                                .password("123")
                                .specialization(Specialization.NEUROLOGY)
                                .build(),
                        Doctor.builder()
                                .login("Doctor2")
                                .name("Doctor2")
                                .surname("Doctor2")
                                .email("Email1@email.com")
                                .password("123")
                                .specialization(Specialization.NEUROLOGY)
                                .build()

                ));

        var doctors = doctorService.getDoctorsBySpecialization(Specialization.NEUROLOGY);
        Assertions.assertEquals(2, doctors.size());

    }


    @Test
    public void addDoctorTest() {

        Doctor doctor = Doctor.builder()
                .login("Doctor1")
                .name("Doctor1")
                .surname("Doctor1")
                .email("Email1@email.com")
                .password("123")
                .specialization(Specialization.NEUROLOGY)
                .build();

        Mockito
                .when(doctorRepository.save(doctor))
                .thenReturn(doctor);

        var doctorFromService = doctorService.addDoctor(ModelMapper.fromDoctorToDoctorDto(doctor));
        Assertions.assertEquals(doctor.getName(),doctorFromService.getName());
        Assertions.assertEquals(doctor.getSpecialization(),doctorFromService.getSpecialization());
        Assertions.assertEquals(doctor.getSurname(),doctorFromService.getSurname());

    }

}
