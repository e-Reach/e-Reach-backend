package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.CreateDoctorRequest;
import org.ereach.inc.data.dtos.response.CreateDoctorResponse;
import org.ereach.inc.data.models.DoctorStatus;
import org.ereach.inc.data.models.users.Doctor;
import org.ereach.inc.data.repositories.users.EReachDoctorsRepository;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.notifications.EReachNotificationRequest;
import org.ereach.inc.services.validators.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static org.ereach.inc.utilities.Constants.DOCTOR_ACCOUNT_CREATED_SUCCESSFULLY;
import static org.ereach.inc.utilities.Constants.DOCTOR_REGISTRATION_AWAITING_CONFIRMATION;
import static org.ereach.inc.utilities.DoctorIdentificationNumberGenerator.generateUniqueDIN;

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
    public CreateDoctorResponse registerNewDoctor(CreateDoctorRequest registerDoctorRequest) throws EReachBaseException {
        CreateDoctorResponse createDoctorResponse;
        try {
            validator.validateEmail(registerDoctorRequest.getEmail());
            Doctor doctor = modelMapper.map(registerDoctorRequest, Doctor.class);
            doctor.setDoctorIdentificationNumber(generateUniqueDIN(fullName(registerDoctorRequest), registerDoctorRequest.getPhoneNumber()));
            doctor.setDoctorStatus(DoctorStatus.PENDING);
            Doctor savedDoctor = doctorRepository.save(doctor);
            createDoctorResponse = modelMapper.map(savedDoctor, CreateDoctorResponse.class);
            createDoctorResponse.setMessage(String.format(DOCTOR_REGISTRATION_AWAITING_CONFIRMATION, fullName(registerDoctorRequest)));
            testDIN = doctor.getDoctorIdentificationNumber();

        }
        catch (Throwable baseException) {
            log.error(baseException.getMessage(), baseException);
            throw new EReachBaseException(baseException);
        }
        return createDoctorResponse;
    }
    private String fullName(CreateDoctorRequest createDoctorRequest) {
        return createDoctorRequest.getFirstName()  + " " + createDoctorRequest.getLastName();
    }

}
