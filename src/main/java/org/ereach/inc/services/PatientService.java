package org.ereach.inc.services;

import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;

public interface PatientService  {
    CreatePatientResponse createPatient(CreatePatientRequest createPatientRequest);
}
