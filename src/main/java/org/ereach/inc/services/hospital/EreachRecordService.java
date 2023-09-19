package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Record;
import org.springframework.stereotype.Component;

@Component

public class EreachRecordService implements RecordService {
    private Record record;
    @Override
    public CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest) {
        return null;
    }

    @Override
    public MedicalLogResponse addLogToRecord(String patientIdentificationNumber, MedicalLog medicalLog) {
        return null;
    }
}
