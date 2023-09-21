package org.ereach.inc.services.hospital;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.repositories.hospital.EReachRecordRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EReachRecordService implements RecordService {

    private EReachRecordRepository eReachRecordRepository;
    private ModelMapper modelMapper;

    @Override
    public CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest) {

        return null;
    }

//    @Override
//    public MedicalLog addNewLogToRecord() {
//        Record record = eReachRecordRepository.findByPatientIdentificationNumber(patientIdentificationNumber);
//        List<MedicalLog> medicalLogList = record.getMedicalLogs();
//        medicalLogList.add(medicalLog);
//        eReachRecordRepository.save(record);
//        return medicalLog;
//    }
}

