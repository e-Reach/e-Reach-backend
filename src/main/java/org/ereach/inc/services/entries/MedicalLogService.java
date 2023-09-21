package org.ereach.inc.services.entries;

import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.models.entries.MedicalLog;

import java.time.LocalTime;

public interface MedicalLogService {

    LocalTime createNewLog(CreateMedicalLogRequest createLogRequest);
    void deActivateAllActiveLogs();

    void deActivateMedicalLogWhosePatientsAreNotDeactivate();
}
