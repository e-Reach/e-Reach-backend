package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.repositories.entries.EReachMedicalLogRepository;
import org.ereach.inc.data.repositories.hospital.EReachRecordRepository;
import org.ereach.inc.services.entries.MedicalLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService implements RecordService {
    @Autowired
    private EReachRecordRepository eReachRecordRepository;


    @Override
    public CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest) {

        return null;
    }

    @Override
    public MedicalLog addNewLogToRecord(String patientIdentificationNumber, MedicalLog medicalLog) {
    Record record = eReachRecordRepository.findByPatientIdentificationNumber(patientIdentificationNumber);
    List<MedicalLog> medicalLogList = record.getMedicalLogs();
    medicalLogList.add(medicalLog);
    eReachRecordRepository.save(record);
    return medicalLog;
//       EReachMedicalLogRepository eReachMedicalLogRepository = GetRecordResponse.

//        Record record = new Record();
//            record.addNewLogToRecord();

//Record newRecord = Record.builder()
//        .dateCreated(LocalDate.now())
//        .centreCreated(Hospital.builder()
//                .build())
//        .build();
//        return null;
    }

//    @Override
//    public CreateRecordResponse addNewlogToRecord(CreateRecordRequest createRecordRequest) {
//
//        return medicalLogRepository.save(addNewLogToRecord());
//    }
}

