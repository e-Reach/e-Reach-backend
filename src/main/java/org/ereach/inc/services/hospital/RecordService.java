package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Record;

import java.util.List;

public interface RecordService {
    CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest);
    
    Record createRecord(String hospitalEmail, String patientIdentificationNumber);
    
    void addLogToRecord(String patientIdentificationNumber, MedicalLog medicalLog);
    GetRecordResponse findRecordByPatientIdentificationNumber(String patientIdentificationNumber);
}
