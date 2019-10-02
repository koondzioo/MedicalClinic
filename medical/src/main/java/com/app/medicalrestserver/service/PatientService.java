package com.app.medicalrestserver.service;


import com.app.medicalrestserver.dto.PatientDto;
import com.app.medicalrestserver.dto.mappers.ModelMapper;
import com.app.medicalrestserver.exceptions.MyException;
import com.app.medicalrestserver.model.Patient;
import com.app.medicalrestserver.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientDto addPatient(PatientDto patientDto) {
        try {
            if (patientDto == null && patientDto.getId() != null) {
                throw new NullPointerException("PATIENT OBJECT IS NOT CORRECT");
            }
            Patient patient1 = ModelMapper.fromPatientDtoToPatient(patientDto);
            patientRepository.save(patient1);
            return ModelMapper.fromPatientToPatientDto(patient1);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MyException("CREATE PATIENT EXCEPTION");
        }
    }

    public List<PatientDto> getAllPatients() {
        try {
            return patientRepository
                    .findAll()
                    .stream()
                    .map(ModelMapper::fromPatientToPatientDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MyException("GET ALL PATIENTS");
        }
    }


    public PatientDto deletePatient(Long id) {
        try {
            System.out.println(id);
            System.out.println(patientRepository.findById(id));
            Patient patient = patientRepository.findById(id).orElseThrow(NullPointerException::new);
            patientRepository.delete(patient);
            return ModelMapper.fromPatientToPatientDto(patient);
        }catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new MyException("DELETE PATIENT");
        }
    }

    public PatientDto findPatientById(Long id){
        try {
            return ModelMapper.fromPatientToPatientDto(patientRepository.findById(id).orElseThrow(NullPointerException::new));
        }catch (Exception e) {
            log.error(e.getMessage());
            throw new MyException("FIND PATIENT BY ID");
        }
    }
}
