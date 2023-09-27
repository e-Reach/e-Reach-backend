package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.dtos.response.GetPatientResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public interface PatientService  {
    CreatePatientResponse createPatient(CreatePatientRequest createPatientRequest) throws EReachBaseException;

    GetPatientResponse findByPatientIdentificationNumber(String patientIdentificationNumber);
    ResponseEntity<?> uploadProfile(MultipartFile multipartFile) throws EReachBaseException;
    GetRecordResponse viewRecord(String patientIdentificationNumber) throws RequestInvalidException;
    GetPatientResponse findById(String id);
    MedicalLogResponse viewMedicalLog(String patientIdentificationNumber, LocalDate date);
}
