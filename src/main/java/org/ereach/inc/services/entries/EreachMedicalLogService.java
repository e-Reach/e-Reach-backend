package org.ereach.inc.services.entries;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.GetPatientResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.repositories.entries.EReachMedicalLogRepository;
import org.ereach.inc.services.hospital.RecordService;
import org.ereach.inc.services.users.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@AllArgsConstructor
public class EreachMedicalLogService implements MedicalLogService {
    private EReachMedicalLogRepository medicalLogRepository;
    private PatientService patientService;
    private ModelMapper modelMapper;
    private RecordService recordService;

    @Override
    public LocalTime createNewLog(CreateMedicalLogRequest createLogRequest) {
//      TODO: Build a new medical log object
        MedicalLog medicalLog = buildMedicalLog(createLogRequest);
        medicalLogRepository.save(medicalLog);
//      TODO: Identify the patient you want to create a log for
        GetPatientResponse foundPatient = patientService.findByPatientIdentificationNumber(createLogRequest.getPatientIdentificationNumber());
//      TODO: Add the log to the patient record
        return recordService.addNewLogToRecord(foundPatient.getPatientIdentificationNumber(), medicalLog).getTimeCreated();
    }

    private static MedicalLog buildMedicalLog(CreateMedicalLogRequest createLogRequest) {
        return MedicalLog.builder()
                         .dateCreated(LocalDate.now())
                         .patientIdentificationNumber(createLogRequest.getPatientIdentificationNumber())
                         .isActive(true)
                         .timeCreated(LocalTime.now())
                         .build();
    }


    @Override
    public void deActivateAllActiveLogs() {


    }

    @Override
    public void deActivateMedicalLogWhosePatientsAreNotDeactivate() {

    }

}