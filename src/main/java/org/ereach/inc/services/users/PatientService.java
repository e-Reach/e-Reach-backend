package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.dtos.response.GetPatientResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
import org.ereach.inc.exceptions.EReachBaseException;

import java.time.LocalDate;

public interface PatientService  {
    CreatePatientResponse createPatient(CreatePatientRequest createPatientRequest) throws EReachBaseException;

    GetPatientResponse findByPatientIdentificationNumber(String patientIdentificationNumber);
    GetPatientResponse findById(String id);
    GetRecordResponse viewRecord(String patientIdentificationNumber);
    MedicalLogResponse viewMedicalLog(String patientIdentificationNumber, LocalDate date);
}
