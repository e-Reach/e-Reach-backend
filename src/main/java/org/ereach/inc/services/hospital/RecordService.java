package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
<<<<<<< HEAD
import org.ereach.inc.data.dtos.response.GetRecordResponse;

public interface RecordService {
    CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest);

    GetRecordResponse findRecordByPatientIdentificationNumber(String patientIdentificationNumber);
=======
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.exceptions.EReachBaseException;

public interface RecordService {
    CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest);
    void addLogToRecord(String patientIdentificationNumber, MedicalLog medicalLog);

>>>>>>> 9dfab38415ecb2a9bd8323675d81a4035c86bffd
}
