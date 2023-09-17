package org.ereach.inc.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.models.PersonalInfo;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.models.users.Patient;
import org.ereach.inc.data.repositories.EReachPersonalInfoRepository;
import org.ereach.inc.data.repositories.hospital.EReachRecordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.ereach.inc.utilities.PatientIdentificationNumberGenerator.generateUniquePIN;
import static org.ereach.inc.utilities.UsernameGenerator.generateUniqueUsername;


@Service
@AllArgsConstructor
public class EReachPatientService implements PatientService{

    private EReachRecordRepository recordRepository;
    private EReachPersonalInfoRepository personalInfoRepository;

    @Getter
    private static String testPIN;
    @Getter
    private static String testUsername;
    @Override
    public CreatePatientResponse createPatient(CreatePatientRequest createPatientRequest) {
        Record savedRecord = recordRepository.save(buildPatientRecord());
        PersonalInfo patientPersonalInfo = personalInfoRepository.save(buildPatientPersonalInfo());
        Patient patient = new Patient();
        String fullName = createPatientRequest.getFirstName()+createPatientRequest.getLastName();
        patient.setFirstName(createPatientRequest.getFirstName());
        patient.setLastName(createPatientRequest.getLastName());
        patient.setNin(createPatientRequest.getNin());
        patient.setPatientIdentificationNumber(generateUniquePIN(fullName, createPatientRequest.getPhoneNumber()));
        patient.setRecord(savedRecord);
        patient.setPersonalInfo(patientPersonalInfo);
        patient.setEReachUsername(generateUniqueUsername(fullName, patient.getPatientIdentificationNumber()));

        testPIN = patient.getPatientIdentificationNumber();
        return null;
    }

    private PersonalInfo buildPatientPersonalInfo() {
        return PersonalInfo.builder()
                .email("")
                .allergy("")
                .knownHealthConditions("")
                .allergy("")
                .phoneNumber("")
                .dateOfBirth(LocalDate.now())
                .build();
    }

    private Record buildPatientRecord() {
        return Record.builder()
                .dateCreated(LocalDate.now())
                .lastTimeUpdated(LocalTime.now())
                .medicalLogs(new ArrayList<>())
                .build();
    }
}
