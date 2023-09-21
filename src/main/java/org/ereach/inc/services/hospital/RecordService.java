package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.models.entries.MedicalLog;

public interface RecordService {


    CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest);


    MedicalLog addNewLogToRecord(String patientIdentificationNumber, MedicalLog medicalLog);

//    CreateRecordResponse addNewlogToRecord(CreateRecordRequest createRecordRequest);

}
