package org.ereach.inc.services.entries;

import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
<<<<<<< HEAD
import org.ereach.inc.data.models.entries.MedicalLog;

import java.time.LocalTime;
=======
import org.ereach.inc.exceptions.EReachBaseException;
>>>>>>> 9dfab38415ecb2a9bd8323675d81a4035c86bffd

public interface MedicalLogService {

    MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest) throws EReachBaseException;
    void deActivateAllActiveLogs();

    void deActivateMedicalLogWhosePatientsAreNotDeactivate();
}
