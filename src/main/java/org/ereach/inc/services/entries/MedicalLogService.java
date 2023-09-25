package org.ereach.inc.services.entries;

import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.exceptions.EReachBaseException;

import java.time.LocalDate;
import java.util.List;

public interface MedicalLogService {

    MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest) throws EReachBaseException;
    void deActivateAllActiveLogs();
    List<MedicalLogResponse> viewPatientMedicalLogs(String patientIdentificationNumber);
    MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date);
    List<MedicalLogResponse> viewPatientsMedicalLogs(String hospitalEmail);

    void deActivateMedicalLogWhosePatientsAreNotDeactivate();
}
