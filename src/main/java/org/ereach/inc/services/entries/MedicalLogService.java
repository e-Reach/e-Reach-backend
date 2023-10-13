package org.ereach.inc.services.entries;

<<<<<<< HEAD
import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.GetMedicalLogResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;

import java.util.List;
=======
import org.ereach.inc.data.dtos.request.entries.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
>>>>>>> 7b64573a0aed6932440053c906836aad320d8048
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.RequestInvalidException;

import java.time.LocalDate;
import java.util.List;

public interface MedicalLogService {

    MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest) throws EReachBaseException;
    void deActivateAllActiveLogs();
<<<<<<< HEAD
    List<GetMedicalLogResponse> getAllLogs();
    List<GetMedicalLogResponse> getPatientMedicalLogs(String patientIdentificationNumber);
=======
    List<MedicalLogResponse> viewPatientMedicalLogs(String patientIdentificationNumber) throws RequestInvalidException;
    MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date);
>>>>>>> 7b64573a0aed6932440053c906836aad320d8048

    void deActivateMedicalLogWhosePatientsAreNotDeactivate();
	
	List<MedicalLogResponse> viewPatientsMedicalLogs(String hospitalId);
}
