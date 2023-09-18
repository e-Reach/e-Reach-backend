package org.ereach.inc.services.entries;

import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;

public interface MedicalLogService {

    MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest);
    void deActivateAllActiveLogs();

    void deActivateMedicalLogWhosePatientsAreNotDeactivate();
}
