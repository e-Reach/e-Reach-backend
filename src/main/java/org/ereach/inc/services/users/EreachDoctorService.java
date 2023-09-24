package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.data.models.AccountStatus;
import org.ereach.inc.data.models.users.Doctor;
import org.ereach.inc.data.repositories.users.EReachDoctorsRepository;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.services.validators.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static org.ereach.inc.utilities.Constants.DOCTOR_REGISTRATION_AWAITING_CONFIRMATION;
import static org.ereach.inc.utilities.PractitionerIdentificationNumberGenerator.generateUniquePIN;

@Service
@AllArgsConstructor
@Slf4j
public class EreachDoctorService implements DoctorService {
    private final EReachDoctorsRepository doctorRepository;
    private ModelMapper modelMapper;
    private EmailValidator validator;
    @Getter
    private static String testDIN;
    
    @Override
    public PractitionerResponse registerNewDoctor(CreatePractitionerRequest registerDoctorRequest) throws EReachBaseException {
        PractitionerResponse createDoctorResponse;
        try {
            validator.validateEmail(registerDoctorRequest.getEmail());
            Doctor doctor = modelMapper.map(registerDoctorRequest, Doctor.class);
            doctor.setPractitionerIdentificationNumber(generateUniquePIN(fullName(registerDoctorRequest), registerDoctorRequest.getEmail()));
            doctor.setStatus(AccountStatus.PENDING);
            Doctor savedDoctor = doctorRepository.save(doctor);
            createDoctorResponse = modelMapper.map(savedDoctor, PractitionerResponse.class);
            createDoctorResponse.setMessage(String.format(DOCTOR_REGISTRATION_AWAITING_CONFIRMATION, fullName(registerDoctorRequest)));
            testDIN = doctor.getPractitionerIdentificationNumber();
        }
        catch (Throwable baseException) {
            log.error(baseException.getMessage(), baseException);
            throw new EReachBaseException(baseException);
        }
        return createDoctorResponse;
    }
    
    @Override
    public PractitionerResponse activatePractitionerAccount(CreatePractitionerRequest registerDoctorRequest) {
        return null;
    }
    
    @Override
    public void removePractitionerByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber) {
    
    }
    
    private String fullName(CreatePractitionerRequest createDoctorRequest) {
        return createDoctorRequest.getFirstName()  + " " + createDoctorRequest.getLastName();
    }

}
