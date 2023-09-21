package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.exceptions.EReachBaseException;

public interface RecordService {
<<<<<<< HEAD
=======
    CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest);
    void addLogToRecord(String patientIdentificationNumber, MedicalLog medicalLog);
>>>>>>> 9dfab38415ecb2a9bd8323675d81a4035c86bffd


    CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest);
}
