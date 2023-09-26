package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.CreateDoctorRequest;
import org.ereach.inc.data.dtos.response.CreateDoctorResponse;
import org.ereach.inc.data.models.users.Doctor;
import org.ereach.inc.data.repositories.users.EReachDoctorsRepository;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.notifications.EReachNotificationRequest;
import org.ereach.inc.services.notifications.MailService;
import org.ereach.inc.services.validators.EmailValidator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static org.ereach.inc.utilities.Constants.DOCTOR_ACCOUNT_CREATED_SUCCESSFULLY;
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
    private MailService mailService;
    @Override
    public CreateDoctorResponse registerNewDoctor(CreateDoctorRequest registerDoctorRequest) throws EReachBaseException {
        CreateDoctorResponse createDoctorResponse;
        try {
            validator.validateEmail(registerDoctorRequest.getEmail());
            Doctor doctor = modelMapper.map(registerDoctorRequest, Doctor.class);
            doctor.setDoctorIdentificationNumber(generateUniqueDIN(fullName(registerDoctorRequest), registerDoctorRequest.getPhoneNumber()));
            Doctor savedDoctor = doctorRepository.save(doctor);
            createDoctorResponse = modelMapper.map(savedDoctor, CreateDoctorResponse.class);
            createDoctorResponse.setMessage(String.format(DOCTOR_ACCOUNT_CREATED_SUCCESSFULLY, fullName(registerDoctorRequest)));
            testDIN = doctor.getDoctorIdentificationNumber();
            sendDoctorId(doctor.getDoctorIdentificationNumber(), doctor.getEmail(),
                    fullName(registerDoctorRequest), "hospitalName");
            doctorRepository.save(doctor);
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

    public void sendDoctorId(String doctorIdentificationNumber, String email, String fullName, String hospitalName) throws RequestInvalidException {
        EReachNotificationRequest request = EReachNotificationRequest.builder()
                .fullName(fullName)
                .username(doctorIdentificationNumber)
                .email(email)
                .build();
        mailService.sendDoctorIdentificationNumber(request, hospitalName);
    }

}
