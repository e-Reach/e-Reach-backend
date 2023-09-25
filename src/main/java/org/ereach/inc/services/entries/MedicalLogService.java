package org.ereach.inc.services.entries;

import org.ereach.inc.data.dtos.request.entries.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.RequestInvalidException;

import java.time.LocalDate;
import java.util.List;

public interface MedicalLogService {

    MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest) throws EReachBaseException;
    void deActivateAllActiveLogs();
    List<MedicalLogResponse> viewPatientMedicalLogs(String patientIdentificationNumber) throws RequestInvalidException;
    MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date);

    void deActivateMedicalLogWhosePatientsAreNotDeactivate();
}
