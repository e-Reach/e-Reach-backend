package org.ereach.inc.services.users;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.request.CreatePersonalInfoRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.dtos.response.entries.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.PatientIdentificationNumberGenerator.generateUniquePIN;
import static org.ereach.inc.utilities.UsernameGenerator.generateUniqueUsername;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;


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
    private EReachConfig config;
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
            response = map(savedPatient);
            response.setMessage(String.format(PATIENT_ACCOUNT_CREATED_SUCCESSFULLY, fullName(request)));
            testPIN = patient.getPatientIdentificationNumber();
            testUsername = patient.getPatientIdentificationNumber();
            sendPatientIdAndUsername(patient.getFirstName(), patient.getEReachUsername(),
                    patient.getPatientIdentificationNumber(), fullName(request), patient.getEmail());
        } catch (Throwable baseException) {
           log.error(baseException.getMessage(), baseException);
           throw new EReachBaseException(baseException);
        }
        return response;
    }

    private CreatePatientResponse map(Patient savedPatient) {
        return CreatePatientResponse.builder()
                .eReachUsername(savedPatient.getEReachUsername())
                .lastName(savedPatient.getLastName())
                .firstName(savedPatient.getFirstName())
                .patientIdentificationNumber(savedPatient.getPatientIdentificationNumber())
                .email(savedPatient.getEmail())

                .build();
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
    public ResponseEntity<?> uploadProfile(MultipartFile multipartFile) throws EReachBaseException {
        String url = pushToCloud(multipartFile);
        return new ResponseEntity<>(url, HttpStatus.BAD_REQUEST);
    }

    private String pushToCloud(MultipartFile logo) throws EReachBaseException {
        Cloudinary cloudinary = new Cloudinary();
        Uploader uploader = cloudinary.uploader();
        Map<String, Object> map = new HashMap<>();
        map.put("public_id","e-Reach/hospital/media/"+logo.getName());
        map.put("api_key",config.getCloudApiKey());
        map.put("api_secret",config.getCloudApiSecret());
        map.put("cloud_name",config.getCloudApiName());
        map.put("secure",true);
        map.put("resource_type", "auto");
        try{
            Map<?,?> response = uploader.upload(logo.getBytes(), map);
            return response.get("url").toString();
        }catch (IOException exception){
            throw new EReachBaseException(exception+" File upload failed");
        }
    }

    @Override
    public GetPatientResponse findById(String id) {
        Optional<Patient> foundPatient = patientsRepository.findById(id);
        return foundPatient.map(patient -> modelMapper.map(patient, GetPatientResponse.class))
                           .orElseThrow(()-> new EReachUncheckedBaseException(String.format(PATIENT_WITH_ID_DOES_NOT_EXIST, id)));
    }

    @Override
    public GetRecordResponse viewRecord(String patientIdentificationNumber) throws RequestInvalidException {
        Optional<Patient> foundPatient = patientsRepository.findByPatientIdentificationNumber(patientIdentificationNumber);
        GetRecordResponse recordResponse = new GetRecordResponse();
        List<MedicalLogResponse> logs;
        if (foundPatient.isPresent()){
            Record record = getRecord(foundPatient.get());
            recordResponse.setDateCreated(record.getDateCreated());
            recordResponse.setLastTimeUpdated(record.getLastTimeUpdated());
            recordResponse.setPatientIdentificationNumber(record.getPatientIdentificationNumber());
            logs = record.getMedicalLogs()
                         .stream()
                         .map(medicalLog -> {
                             MedicalLogResponse medicalLogResponse = new MedicalLogResponse();
                             List<TestResponseDTO> testResponses = mapList(medicalLog.getTests(), TestResponseDTO.class);
                             List<PrescriptionsResponseDTO> prescriptionResponses = mapList(medicalLog.getPrescriptions(), PrescriptionsResponseDTO.class);
                             VitalsResponseDTO vitalsResponse = modelMapper.map(medicalLog.getVitals(), VitalsResponseDTO.class);
                             DoctorReportResponseDTO doctorsReport = modelMapper.map(medicalLog.getDoctorsReport(), DoctorReportResponseDTO.class);

                             medicalLogResponse.setDoctorReportResponseDTO(doctorsReport);
                             medicalLogResponse.setPrescriptionsResponseDTO(prescriptionResponses);
                             medicalLogResponse.setVitalsResponseDTO(vitalsResponse);
                             medicalLogResponse.setTestResponseDTO(testResponses);

                             return medicalLogResponse;
                         }).toList();
            recordResponse.setMedicalLogResponses(logs);
            return recordResponse;
        }
        throw new RequestInvalidException(String.format(PATIENT_WITH_ID_DOES_NOT_EXIST, patientIdentificationNumber));
    }

    private Record getRecord(Patient patient) {
        return patient.getRecord();
    }

    @Override
    public MedicalLogResponse viewMedicalLog(String patientIdentificationNumber, LocalDate date) {
        return null;
    }

    public void sendPatientIdAndUsername(String firstName, String username, String pin, String fullName, String email) throws RequestInvalidException {
        EReachNotificationRequest request = EReachNotificationRequest.builder()
                .firstName(fullName)
                .password(pin)
                .email(email)
                .username(username)
                .build();
        mailService.sendPatientInfo(request, email);
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

    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source.stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }

}
