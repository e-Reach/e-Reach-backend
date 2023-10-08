package org.ereach.inc.services.hospital;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.dtos.response.entries.*;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.repositories.hospital.EReachRecordRepository;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.ereach.inc.utilities.Constants.RECORD_WITH_P_I_N_NOT_FOUND;

@Service
@AllArgsConstructor
public class EReachRecordService implements RecordService {
    
    private EReachRecordRepository recordRepository;
    private ModelMapper modelMapper;
    private HospitalService hospitalService;
    
    //TODO: 9/25/2023 TO DELETE CAUSE IT's NO MORE NECESSARY
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
    
    @Override
    public GetRecordResponse findRecordByPatientIdentificationNumber(String patientIdentificationNumber) {
        Optional<Record> foundRecord = recordRepository.findByPatientIdentificationNumber(patientIdentificationNumber);
        return foundRecord.map(record -> {
            GetRecordResponse mappedResponse = modelMapper.map(foundRecord, GetRecordResponse.class);
            List<MedicalLogResponse> listOfLogs = record.getMedicalLogs().stream().map(medicalLog -> {
                VitalsResponseDTO vitals = modelMapper.map(medicalLog.getVitals(), VitalsResponseDTO.class);
                DoctorReportResponseDTO docReport = modelMapper.map(medicalLog.getDoctorsReport(), DoctorReportResponseDTO.class);
                List<TestResponseDTO> tests = mapList(medicalLog.getTests(), TestResponseDTO.class);
                List<PrescriptionsResponseDTO> prescriptions = mapList(medicalLog.getPrescriptions(), PrescriptionsResponseDTO.class);
                return MedicalLogResponse.builder()
                               .prescriptionsResponseDTO(prescriptions)
                               .patientIdentificationNumber(medicalLog.getPatientIdentificationNumber())
                               .doctorReportResponseDTO(docReport)
                               .timeCreated(medicalLog.getTimeCreated())
                               .dateCreated(medicalLog.getDateCreated())
                               .hospitalEmail(medicalLog.getHospitalEmail())
                               .message("Medical Log Found")
                               .testResponseDTO(tests)
                               .vitalsResponseDTO(vitals)
                               .build();
            }).toList();
            mappedResponse.setMedicalLogResponses(listOfLogs);
            mappedResponse.setMessage("Record found Successfully");
            return mappedResponse;
        }).orElseThrow(()->new EntityNotFoundException(String.format(RECORD_WITH_P_I_N_NOT_FOUND, patientIdentificationNumber)));

    }
    
    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream()
                       .map(element -> modelMapper.map(element, targetClass))
                       .collect(Collectors.toList());
    }
}
