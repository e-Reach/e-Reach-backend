package org.ereach.inc.services;

import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.models.users.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service

public class EReachPatientService implements PatientService{
    @Override
    public CreatePatientResponse createPatient(CreatePatientRequest createPatientRequest) {
        Record record = new Record();
        record.setDateCreated(LocalDate.now());
        record.setLastTimeUpdated(LocalTime.now());
        record.
        Patient patient = new Patient();
        patient.setFirstName(createPatientRequest.getFirstName());
        patient.setLastName(createPatientRequest.getLastName());
        patient.setNin(createPatientRequest.getNin());
        patient.setPatientIdentificationNumber(generatedPatientIdentificationNumber());
        patient.setRecord(record);
        patient.setPersonalInfo(personalInfo);
        patient.setEReachUsername(ereachUsername);

        return null;
    }
}
