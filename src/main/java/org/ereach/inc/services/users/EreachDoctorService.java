package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.ereach.inc.services.hospital.RecordService;
import org.ereach.inc.services.validators.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class EreachDoctorService implements DoctorService {
    private final EReachPractitionerRepository practitionerRepository;
    private ModelMapper modelMapper;
    private EmailValidator validator;
    private RecordService recordService;
    @Getter
    private static String testDIN;
    
    
    @Override
    public GetRecordResponse viewPatientRecord(String patientIdentificationNumber) {
        recordService.viewPatientRecord(patientIdentificationNumber);
        return null;
    }
    
    @Override
    public MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date) {
        return null;
    }
}
