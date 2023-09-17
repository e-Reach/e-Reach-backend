package org.ereach.inc.services;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.models.users.Patient;
import org.ereach.inc.data.repositories.hospital.EReachRecordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


@Service
@AllArgsConstructor
public class EReachPatientService implements PatientService{

    private EReachRecordRepository recordRepository;
    private ModelMapper modelMapper;
    @Override
    public CreatePatientResponse createPatient(CreatePatientRequest createPatientRequest) {
        Record savedRecord = recordRepository.save(buildPatientRecord());
        Patient patient = new Patient();
        patient.setFirstName(createPatientRequest.getFirstName());
        patient.setLastName(createPatientRequest.getLastName());
        patient.setNin(createPatientRequest.getNin());
        patient.setPatientIdentificationNumber(generatedPatientIdentificationNumber());
        patient.setRecord(savedRecord);
        patient.setPersonalInfo(personalInfo);
        patient.setEReachUsername(ereachUsername);

        return null;
    }

    private Record buildPatientRecord() {
        return Record.builder()
                .dateCreated(LocalDate.now())
                .lastTimeUpdated(LocalTime.now())
                .medicalLogs(new ArrayList<>())
                .build();
    }
}
