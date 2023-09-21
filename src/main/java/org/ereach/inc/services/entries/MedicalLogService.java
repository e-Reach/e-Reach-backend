package org.ereach.inc.services.entries;

import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.exceptions.EReachBaseException;

public interface MedicalLogService {

    MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest) throws EReachBaseException;
    void deActivateAllActiveLogs();

    void deActivateMedicalLogWhosePatientsAreNotDeactivate();
}
