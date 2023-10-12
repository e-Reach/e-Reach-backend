package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.ereach.inc.services.entries.MedicalLogService;
import org.ereach.inc.services.hospital.RecordService;
import org.ereach.inc.services.validators.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EreachDoctorService implements DoctorService {
    private final EReachPractitionerRepository practitionerRepository;
    private MedicalLogService medicalLogService;
    private ModelMapper modelMapper;
    private EmailValidator validator;
    private RecordService recordService;
    
    
    @Override
    public GetRecordResponse viewPatientRecord(String patientIdentificationNumber) {
        return recordService.findRecordByPatientIdentificationNumber(patientIdentificationNumber);
    }
    
    @Override
    public MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date) {
        return medicalLogService.viewPatientMedicalLog(patientIdentificationNumber, date);
    }
}
