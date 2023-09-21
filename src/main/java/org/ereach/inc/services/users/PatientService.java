package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.dtos.response.GetPatientResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.exceptions.EReachBaseException;

public interface PatientService  {
    CreatePatientResponse createPatient(CreatePatientRequest createPatientRequest) throws EReachBaseException;

    GetPatientResponse findByPatientIdentificationNumber(String patientIdentificationNumber);
    GetPatientResponse findById(String id);
}
