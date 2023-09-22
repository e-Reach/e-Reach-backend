package org.ereach.inc.services.entries;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.GetMedicalLogResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.entries.Entry;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.repositories.entries.EReachMedicalLogRepository;
import org.ereach.inc.data.repositories.hospital.EReachRecordRepository;
import org.ereach.inc.data.repositories.users.EReachPatientsRepository;
import org.ereach.inc.services.hospital.RecordService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EreachMedicalLogService implements MedicalLogService {

    private EReachMedicalLogRepository medicalLogRepository;
    private EReachPatientsRepository patientsRepository;
    private RecordService recordService;
    private ModelMapper modelMapper;


    @Override
    public MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest) {
        MedicalLog medicalLog = mapMedicalLog(createLogRequest);
//      Meet the record keeper to help you find a record with the patient P.I.N
//      If the record is found the keeper returns a photocopy of the record found
        GetRecordResponse foundRecord = recordService.findRecordByPatientIdentificationNumber(createLogRequest.getPatientIdentificationNumber());
//      Then we extract some values from the photocopy and map it to the new log we want to create
        Record record = new Record();
        record.setId(foundRecord.getId());
//      We identify the log with a Record
        medicalLog.setMedicalRecord(record);
//      We save the log
        MedicalLog savedMedicalLog = medicalLogRepository.save(medicalLog);

        MedicalLogResponse medicalLogResponse = modelMapper.map(savedMedicalLog, MedicalLogResponse.class);
        return medicalLogResponse;
    }


    @Override
    public void deActivateAllActiveLogs() {
        List<MedicalLog> allLogs = medicalLogRepository.findAll();
        for (MedicalLog log : allLogs) {
            if (log.isActive())
                log.setActive(false);
        }
    }

    @Override
    public List<GetMedicalLogResponse> getAllLogs() {
        return new List<MedicalLog>);
    }

    @Override
    public List<GetMedicalLogResponse> getPatientMedicalLogs(String patientIdentificationNumber) {

        return null;
    }

    private static MedicalLog mapMedicalLog(CreateMedicalLogRequest createLogRequest) {
        MedicalLog medicalLog = new MedicalLog();

        LocalDate localDate = LocalDate.now();
        LocalTime localTime= LocalTime.now();

        medicalLog.setPatientIdentificationNumber(createLogRequest.getPatientIdentificationNumber());

        medicalLog.setDateCreated(localDate);
        medicalLog.setTimeCreated(localTime);
        medicalLog.setActive(true);

        List<Entry>entries =  new ArrayList<>();

        medicalLog.setEntries(entries);

        return medicalLog;
    }

}
