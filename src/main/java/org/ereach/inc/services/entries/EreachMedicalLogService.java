package org.ereach.inc.services.entries;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.response.GetPatientResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.repositories.entries.EReachMedicalLogRepository;
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.services.hospital.RecordService;
import org.ereach.inc.services.users.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EreachMedicalLogService implements MedicalLogService {
    public static final String MEDICAL_LOG_CREATED_SUCCESSFULLY = "Medical log created Successfully";
    private EReachMedicalLogRepository medicalLogRepository;
    private PatientService patientService;
    private ModelMapper modelMapper;
    private RecordService recordService;
    private HospitalService hospitalService;
    @Override
    public MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest){
        MedicalLog medicalLog = buildMedicalLog(createLogRequest);
        GetPatientResponse foundPatient = patientService.findByPatientIdentificationNumber(createLogRequest.getPatientIdentificationNumber());
        MedicalLog savedMedicalLog = medicalLogRepository.save(medicalLog);
        recordService.addLogToRecord(foundPatient.getPatientIdentificationNumber(), savedMedicalLog);
        hospitalService.addToLog(createLogRequest.getHospitalEmail(), savedMedicalLog);
        MedicalLogResponse response = modelMapper.map(savedMedicalLog, MedicalLogResponse.class);
        response.setMessage(MEDICAL_LOG_CREATED_SUCCESSFULLY);
        return response;
    }

    private static MedicalLog buildMedicalLog(CreateMedicalLogRequest createLogRequest) {
        return MedicalLog.builder()
                         .dateCreated(LocalDate.now())
                         .patientIdentificationNumber(createLogRequest.getPatientIdentificationNumber())
                         .isActive(true)
                         .timeCreated(LocalTime.now())
                         .entries(new ArrayList<>())
                         .build();
    }

    @Override
    public void deActivateAllActiveLogs() {

    }
    
    @Override
    public List<MedicalLogResponse> viewPatientMedicalLogs(String patientIdentificationNumber) {
        return null;
    }
    
    @Override
    public MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date) {
        return null;
    }
    
    @Override
    public List<MedicalLogResponse> viewPatientsMedicalLogs(String hospitalEmail) {
        return null;
    }
    
    @Override
    public void deActivateMedicalLogWhosePatientsAreNotDeactivate() {

    }
}
