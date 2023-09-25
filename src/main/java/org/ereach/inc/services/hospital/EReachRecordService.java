package org.ereach.inc.services.hospital;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.repositories.hospital.EReachRecordRepository;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EReachRecordService implements RecordService {
    
    public static final String RECORD_WITH_P_I_N_NOT_FOUND = "Record with p.i.n %s not found";
    private EReachRecordRepository recordRepository;
    private ModelMapper modelMapper;
    private HospitalService hospitalService;
    
    // TODO: 9/25/2023 TO DELETE CAUSE IT's NO MORE NECESSARY
    @Override
    public CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest) {
        HospitalResponse foundHospital = hospitalService.findHospitalByEmail(createRecordRequest.getHospitalEmail());
        Record newRecord = Record.builder()
                                   .dateCreated(LocalDate.now())
                                   .medicalLogs(new ArrayList<>())
                                   .patientIdentificationNumber(createRecordRequest.getPatientIdentificationNumber())
                                   .lastTimeUpdated(LocalTime.now())
                                   .build();
        newRecord.setPatientIdentificationNumber(createRecordRequest.getPatientIdentificationNumber());
        Record savedRecord = newRecord;//recordRepository.save(newRecord);
//        hospitalService.addToRecords(foundHospital.getHospitalEmail(), savedRecord);
        return modelMapper.map(savedRecord, CreateRecordResponse.class);
    }
    
    @Override
    public Record createRecord(String hospitalEmail, String patientIdentificationNumber) {
        Record record = Record.builder()
                              .dateCreated(LocalDate.now())
                              .medicalLogs(new ArrayList<>())
                              .patientIdentificationNumber(patientIdentificationNumber)
                              .lastTimeUpdated(LocalTime.now())
                              .build();
        Record savedRecord = recordRepository.save(record);
        hospitalService.addToRecords(hospitalEmail, savedRecord);
        return savedRecord;
    }

    @Override
    public void addLogToRecord(String patientIdentificationNumber, MedicalLog medicalLog) {
        Optional<Record> foundRecord = recordRepository.findByPatientIdentificationNumber(patientIdentificationNumber);
	    foundRecord.ifPresentOrElse(record -> record.getMedicalLogs()
                                                    .add(medicalLog),
                    ()-> {throw new EReachUncheckedBaseException(String.format(RECORD_WITH_P_I_N_NOT_FOUND, patientIdentificationNumber));});
    }
}
