package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;

public interface RecordService {
    CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest);

    GetRecordResponse findRecordByPatientIdentificationNumber(String patientIdentificationNumber);
}
