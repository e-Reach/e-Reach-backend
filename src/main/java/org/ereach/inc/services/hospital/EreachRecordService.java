package org.ereach.inc.services.hospital;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.repositories.hospital.EReachRecordRepository;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.ereach.inc.services.users.HospitalAdminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EreachRecordService implements RecordService {
    
    public static final String RECORD_WITH_P_I_N_NOT_FOUND = "Record with p.i.n %s not found";
    private HospitalAdminService hospitalAdminService;
    private EReachRecordRepository recordRepository;
    private ModelMapper modelMapper;

    @Override
    public CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest) {
        HospitalResponse foundHospital = hospitalAdminService.getHospitalRegisteredWith(createRecordRequest.getOfficerIdentificationNumber());
        Record newRecord = Record.builder()
                                   .dateCreated(LocalDate.now())
                                   .centreCreated(null)
                                   .medicalLogs(new ArrayList<>())
                                   .lastTimeUpdated(LocalTime.now())
                                   .build();
        newRecord.setPatientIdentificationNumber(createRecordRequest.getPatientIdentificationNumber());
        Record savedRecord = recordRepository.save(newRecord);
        return modelMapper.map(savedRecord, CreateRecordResponse.class);
    }

    @Override
    public GetRecordResponse findRecordByPatientIdentificationNumber(String patientIdentificationNumber) {
        return null;
    }

    @Override
    public void addLogToRecord(String patientIdentificationNumber, MedicalLog medicalLog) {
        Optional<Record> foundRecord = recordRepository.findByPatientIdentificationNumber(patientIdentificationNumber);
	    foundRecord.ifPresentOrElse(record -> record.getMedicalLogs()
                                                    .add(medicalLog),
                    ()-> {throw new EReachUncheckedBaseException(String.format(RECORD_WITH_P_I_N_NOT_FOUND, patientIdentificationNumber));});
    }
}
