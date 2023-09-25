package org.ereach.inc.services.entries;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.entries.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.request.entries.DoctorReportDTO;
import org.ereach.inc.data.dtos.response.GetPatientResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.entries.*;
import org.ereach.inc.data.repositories.entries.EReachEntryRepository;
import org.ereach.inc.data.repositories.entries.EReachMedicalLogRepository;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.services.hospital.RecordService;
import org.ereach.inc.services.users.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.ereach.inc.utilities.Constants.NO_MEDICAL_LOGS_FOUND_FOR_PATIENT;
import static org.ereach.inc.utilities.Constants.NO_MEDICAL_LOGS_FOUND_FOR_PATIENT_WITH_DATE;

@Service
@AllArgsConstructor
public class EreachMedicalLogService implements MedicalLogService {
    public static final String MEDICAL_LOG_CREATED_SUCCESSFULLY = "Medical log created Successfully";
    private EReachMedicalLogRepository medicalLogRepository;
    private PatientService patientService;
    private EReachEntryRepository entryRepository;
    private ModelMapper modelMapper;
    private RecordService recordService;
    private HospitalService hospitalService;
    @Override
    public MedicalLogResponse createNewLog(CreateMedicalLogRequest createLogRequest){
        Vitals mappedVital = modelMapper.map(createLogRequest.getVitalsDTO(), Vitals.class);
        DoctorsReport mappedDocReport = modelMapper.map(createLogRequest.getDoctorReportDTO(), DoctorsReport.class);
        List<Tests> mappedTests = mapList(createLogRequest.getTestDTO(), Tests.class);
        List<Prescription> mappedPrescriptions = mapList(createLogRequest.getPrescriptionsDTO(), Prescription.class);
        MedicalLog medicalLog = buildMedicalLog(createLogRequest);
        Vitals savedVital = entryRepository.save(mappedVital);
        DoctorsReport savedDocReport = entryRepository.save(mappedDocReport);
        List<Tests> savedTests = entryRepository.saveAll(mappedTests);
        List<Prescription> savedPrescriptions = entryRepository.saveAll(mappedPrescriptions);
        medicalLog.getEntries().add(savedVital);
        medicalLog.getEntries().add(savedDocReport);
        medicalLog.getEntries().addAll(savedTests);
        medicalLog.getEntries().addAll(savedPrescriptions);
        System.out.println("\t\t\t\tMedical log "+medicalLog);
        System.out.println("=".repeat(20));
        System.out.println(medicalLog);
        System.out.println("=".repeat(20));
        GetPatientResponse foundPatient = patientService.findByPatientIdentificationNumber(createLogRequest.getPatientIdentificationNumber());
        MedicalLog savedMedicalLog = medicalLogRepository.save(medicalLog);
        recordService.addLogToRecord(foundPatient.getPatientIdentificationNumber(), savedMedicalLog);
        hospitalService.addToLog(createLogRequest.getHospitalEmail(), savedMedicalLog);
        MedicalLogResponse response = modelMapper.map(savedMedicalLog, MedicalLogResponse.class);
        response.setMessage(MEDICAL_LOG_CREATED_SUCCESSFULLY);
        return response;
    }
    
    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream()
                     .map(element -> modelMapper.map(element, targetClass))
                     .collect(Collectors.toList());
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
    public List<MedicalLogResponse> viewPatientMedicalLogs(String patientIdentificationNumber) throws RequestInvalidException {
        List<MedicalLog> medicalLogs = medicalLogRepository.findAllByPatientIdentificationNumber(patientIdentificationNumber);
        if (medicalLogs.isEmpty())
            throw new RequestInvalidException(String.format(NO_MEDICAL_LOGS_FOUND_FOR_PATIENT, patientIdentificationNumber));
        else return medicalLogs.stream()
                               .map(medicalLog -> modelMapper.map(medicalLog, MedicalLogResponse.class))
                               .toList();
    }
    @Override
    public MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date) {
        Optional<MedicalLog> foundLog = medicalLogRepository.findByPatientIdentificationNumberAndDateCreated(patientIdentificationNumber, date);
        return foundLog.map(medicalLog -> modelMapper.map(medicalLog, MedicalLogResponse.class))
                       .orElseThrow(()-> new EReachUncheckedBaseException(String.format(NO_MEDICAL_LOGS_FOUND_FOR_PATIENT_WITH_DATE, patientIdentificationNumber, date)));
    }
    
    @Override
    public void deActivateMedicalLogWhosePatientsAreNotDeactivate() {

    }
}
