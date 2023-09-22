package org.ereach.inc.services.entries;

import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.GetMedicalLogResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.entries.MedicalLog;

import java.util.List;

public interface MedicalLogService {

    MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest);
    void deActivateAllActiveLogs();
    List<GetMedicalLogResponse> getAllLogs();
    List<GetMedicalLogResponse> getPatientMedicalLogs(String patientIdentificationNumber);



}
