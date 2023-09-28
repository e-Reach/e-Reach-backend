package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.request.CreatePersonalInfoRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.dtos.response.GetPatientResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.dtos.response.PersonalInfoResponse;
import org.ereach.inc.data.models.PersonalInfo;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.models.users.Patient;
import org.ereach.inc.data.repositories.EReachPersonalInfoRepository;
import org.ereach.inc.data.repositories.users.EReachPatientsRepository;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.PersonalInfoService;
import org.ereach.inc.services.hospital.EReachRecordService;
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.services.notifications.EReachNotificationRequest;
import org.ereach.inc.services.notifications.MailService;
import org.ereach.inc.services.validators.EmailValidator;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.PatientIdentificationNumberGenerator.generateUniquePIN;
import static org.ereach.inc.utilities.UsernameGenerator.generateUniqueUsername;


@Service
@AllArgsConstructor
@Slf4j
public class EReachPatientService implements PatientService{

    private EReachPersonalInfoRepository personalInfoRepository;
    private ModelMapper modelMapper;
    private EmailValidator validator;
    private PersonalInfoService personalInfoService;
    private MailService mailService;
    private EReachPatientsRepository patientsRepository;
    private EReachRecordService recordService;
    private HospitalService hospitalService;
    @Getter
    private static String testPIN;
    @Getter
    private static String testUsername;

    @Override
    public CreatePatientResponse createPatient(CreatePatientRequest request) throws EReachBaseException {
        HospitalResponse foundHospital = hospitalService.findHospitalByEmail(request.getHospitalEmail());
        CreatePatientResponse response;
        try {
            validator.validateEmail(request.getEmail());
            PersonalInfo savedPersonalInfo = createPersonalInfo(request);
            Patient patient = modelMapper.map(request, Patient.class);
            Patient savedPatient = savedPatient(request, patient, savedPersonalInfo);
            response = modelMapper.map(savedPatient, CreatePatientResponse.class);
            response.setMessage(String.format(PATIENT_ACCOUNT_CREATED_SUCCESSFULLY, fullName(request)));
            testPIN = patient.getPatientIdentificationNumber();
            testUsername = patient.getPatientIdentificationNumber();
            sendPatientIdAndUsername(patient.getEReachUsername(), patient.getPatientIdentificationNumber(),
                    patient.getEmail(), fullName(request), foundHospital.getHospitalName());
        } catch (Throwable baseException) {
           log.error(baseException.getMessage(), baseException);
           throw new EReachBaseException(baseException);
        }
        return response;
    }
    
    @NotNull
    private Patient savedPatient(CreatePatientRequest request, Patient patient, PersonalInfo savedPersonalInfo) {
        patient.setPatientIdentificationNumber(generateUniquePIN(fullName(request), request.getPhoneNumber()));
        Record savedRecord = recordService.createRecord(request.getHospitalEmail(), patient.getPatientIdentificationNumber());
        patient.setRecord(savedRecord);
        patient.setPersonalInfo(savedPersonalInfo);
        patient.setEReachUsername(generateUniqueUsername(fullName(request), patient.getPatientIdentificationNumber()));
	    return patientsRepository.save(patient);
    }
    
    @Override
    public GetPatientResponse findByPatientIdentificationNumber(String patientIdentificationNumber) {
        Optional<Patient> foundPatient = patientsRepository.findByPatientIdentificationNumber(patientIdentificationNumber);
        return foundPatient.map(patient -> modelMapper.map(patient, GetPatientResponse.class))
                           .orElseThrow(()-> new EReachUncheckedBaseException(String.format(PATIENT_WITH_PIN_DOES_NOT_EXIST, patientIdentificationNumber)));
    }
    
    @Override
    public GetPatientResponse findById(String id) {
        Optional<Patient> foundPatient = patientsRepository.findById(id);
        return foundPatient.map(patient -> modelMapper.map(patient, GetPatientResponse.class))
                           .orElseThrow(()-> new EReachUncheckedBaseException(String.format(PATIENT_WITH_ID_DOES_NOT_EXIST, id)));
    }
    
    public void sendPatientIdAndUsername(String eReachUsername, String patientIdentificationNumber, String email, String fullName, String hospitalName) throws RequestInvalidException {
        EReachNotificationRequest request = EReachNotificationRequest.builder()
                .firstName(fullName)
                .password(patientIdentificationNumber)
                .email(email)
                .username(eReachUsername)
                .build();
        mailService.sendPatientInfo(request, hospitalName);
    }

    @NotNull
    private PersonalInfo createPersonalInfo(CreatePatientRequest request) {
        CreatePersonalInfoRequest personalInfoRequest = modelMapper.map(request, CreatePersonalInfoRequest.class);
        PersonalInfoResponse createInfoResponse = personalInfoService.addPersonalInfo(personalInfoRequest);
        PersonalInfo mappedInfo = modelMapper.map(createInfoResponse, PersonalInfo.class);
        return personalInfoRepository.save(mappedInfo);
    }


    private String fullName(CreatePatientRequest request) {
        return request.getFirstName()  + " " + request.getLastName();
    }
}
