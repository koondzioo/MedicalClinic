package com.app.medicalrestserver.service;

import com.app.medicalrestserver.dto.VisitDto;
import com.app.medicalrestserver.dto.mappers.ModelMapper;
import com.app.medicalrestserver.exceptions.MyException;
import com.app.medicalrestserver.model.Doctor;
import com.app.medicalrestserver.model.Patient;
import com.app.medicalrestserver.model.Visit;
import com.app.medicalrestserver.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Component
public class VisitService {

    private final VisitRepository visitRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;


    public VisitDto addVisit(VisitDto visitDto) {
        try {
            if (visitDto == null && visitDto.getId() != null) {
                throw new NullPointerException("VISIT OBJECT IS NOT CORRECT");
            }
            Doctor doctor = ModelMapper.fromDoctorDtoToDoctor(doctorService.findDoctorById(visitDto.getDoctorId()));
            Patient patient = ModelMapper.fromPatientDtoToPatient(patientService.findPatientById(visitDto.getPatientId()));
            Visit visit = ModelMapper.fromVisitDtoToVisit(visitDto);
            visit.setDoctor(doctor);
            visit.setPatient(patient);
            visitRepository.save(visit);
            return ModelMapper.fromVisitToVisitDto(visit);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            throw new MyException("CREATE VISIT EXCEPTION");
        }

    }

    public VisitDto deleteVisit(Long id) {
        try {
            Visit visit = visitRepository.findById(id).orElseThrow(NullPointerException::new);
            visitRepository.delete(visit);
            return ModelMapper.fromVisitToVisitDto(visit);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MyException("DELETE VISIT EXCEPTION");
        }
    }

    public List<VisitDto> getVisitsByPatient(Long id) {
        try {
            Patient patient = ModelMapper.fromPatientDtoToPatient(patientService.findPatientById(id));
            return patient.getVisitList().stream().map(ModelMapper::fromVisitToVisitDto).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new MyException("GET VISITS BY PATIENT");
        }
    }

    public VisitDto getVisitById(Long id) {
        try {
            return ModelMapper.fromVisitToVisitDto(visitRepository.findById(id).orElseThrow(NullPointerException::new));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            throw new MyException("GET VISIT BY ID");
        }
    }
}
