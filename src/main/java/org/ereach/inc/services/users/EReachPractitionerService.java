package org.ereach.inc.services.users;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.*;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.users.Doctor;
import org.ereach.inc.data.models.users.LabTechnician;
import org.ereach.inc.data.models.users.Pharmacist;
import org.ereach.inc.data.models.users.Practitioner;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.services.notifications.EReachNotificationRequest;
import org.ereach.inc.services.notifications.MailService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.ereach.inc.data.models.AccountStatus.ACTIVE;
import static org.ereach.inc.data.models.Role.*;
import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.JWTUtil.*;
import static org.ereach.inc.utilities.PractitionerIdentificationNumberGenerator.generateUniquePIN;

@Service
@AllArgsConstructor
@Slf4j
public class EReachPractitionerService implements PractitionerService{
	private EReachConfig config;
	private ModelMapper mapper;
	private DoctorService doctorService;
	private PharmacistService pharmacistService;
	private MailService mailService;
	private EReachPractitionerRepository practitionerRepository;
	private HospitalService hospitalService;
	private RestTemplate restTemplate;
	private static final Map<String, Practitioner> practitioners = Map.of("doctor", new Doctor(),
																		  "labtechnician", new LabTechnician(),
																		  "pharmacist", new Pharmacist());
	@Override
	public PractitionerResponse invitePractitioner(InvitePractitionerRequest registerDoctorRequest) throws RegistrationFailedException {
		Practitioner practitioner = practitioners.get(registerDoctorRequest.getRole().toLowerCase());
		try {
			Doctor savedDoctor;
			if (practitioner instanceof Doctor) {
				Doctor mappedDoctor = mapper.map(registerDoctorRequest, Doctor.class);
				buildPractitioner(mappedDoctor, DOCTOR);
				savedDoctor = practitionerRepository.save(mappedDoctor);
				savedDoctor.setUserRole(DOCTOR);
				hospitalService.addPractitioners(registerDoctorRequest.getHospitalEmail(),
						savedDoctor);
				PractitionerResponse response = mapper.map(savedDoctor, PractitionerResponse.class);
				response.setMessage(ACCOUNT_ACTIVATION_SUCCESSFUL);
				return response;
			} else if (practitioner instanceof Pharmacist) {
				Pharmacist savedPharmacist;
				Pharmacist mappedPharmacist = mapper.map(registerDoctorRequest, Pharmacist.class);
				buildPractitioner(mappedPharmacist, PHARMACIST);
				savedPharmacist = practitionerRepository.save(mappedPharmacist);
				savedPharmacist.setUserRole(PHARMACIST);
				mailService.sendPractitionerMail(buildNotificationRequest(savedPharmacist));
				hospitalService.addPractitioners(registerDoctorRequest.getHospitalEmail(),
						savedPharmacist);
				PractitionerResponse response = mapper.map(savedPharmacist, PractitionerResponse.class);
				response.setMessage(ACCOUNT_ACTIVATION_SUCCESSFUL);
				return response;
				
			} else {
				LabTechnician mappedLabTechnician = mapper.map(registerDoctorRequest, LabTechnician.class);
				buildPractitioner(mappedLabTechnician, LAB_TECHNICIAN);
				LabTechnician savedLabTechnician = practitionerRepository.save(mappedLabTechnician);
				savedLabTechnician.setUserRole(LAB_TECHNICIAN);
				mailService.sendPractitionerMail(buildNotificationRequest(savedLabTechnician));
				hospitalService.addPractitioners(registerDoctorRequest.getHospitalEmail(),
						savedLabTechnician);
				PractitionerResponse response = mapper.map(savedLabTechnician, PractitionerResponse.class);
				response.setMessage(ACCOUNT_ACTIVATION_SUCCESSFUL);
				return response;
			}
		}catch (Throwable throwable){
			throw new RegistrationFailedException(throwable);
		}
	}
	
	@Override
	public PractitionerLoginResponse login(PractitionerLoginRequest loginRequest) {
		return PractitionerLoginResponse.builder()
				       .practitionerIdentificationNumber(loginRequest.getPractitionerIdentificationNumber())
				       .message("Login Successful")
				       .username(loginRequest.getUsername())
				       .email(loginRequest.getEmail())
				       .build();
	}
	
	private EReachNotificationRequest buildNotificationRequest(Practitioner practitioner) {
		String url = "http://localhost:3000/practitioner-login";
		return EReachNotificationRequest.builder()
								        .firstName(practitioner.getFirstName())
								        .lastName("successfully")
								        .templatePath(PRACTITIONER_ACCOUNT_ACTIVATION_MAIL_PATH)
				                        .url(url)
								        .email(practitioner.getEmail())
								        .username(practitioner.getPractitionerIdentificationNumber())
								        .build();
	}
	
	private static void buildPractitioner(Practitioner mappedPractitioner, Role role) {
		String fullName = mappedPractitioner.getFirstName() + SPACE + mappedPractitioner.getLastName();
		mappedPractitioner.setActive(true);
		mappedPractitioner.setStatus(ACTIVE);
		mappedPractitioner.setPractitionerIdentificationNumber(generateUniquePIN(fullName, mappedPractitioner.getEmail()));
		mappedPractitioner.setUserRole(role);
	}
	
	@Override
	public PractitionerResponse invitePractitioner(String token) throws RequestInvalidException, RegistrationFailedException {
		if (isValidToken(token, config.getAppJWTSecret())){
			InvitePractitionerRequest createPractitionerRequest = buildPractitionerCreationRequest(token);
			return invitePractitioner(createPractitionerRequest);
		}
		throw new RequestInvalidException(String.format(TOKEN_WAS_INVALID, PRACTITIONER));
	}
	
	private InvitePractitionerRequest buildPractitionerCreationRequest(String token) {
		return InvitePractitionerRequest.builder()
				       .lastName(extractLastNameFrom(token))
				       .firstName(extractFirstNameFrom(token))
				       .email(extractEmailFrom(token))
				       .role(extractRoleFrom(token))
				       .build();
	}
	
	@Override
	public void removePractitionerByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber) {
	
	}
	
	@Override
	public GetRecordResponse viewPatientRecord(String patientIdentificationNumber, String role) {
		Practitioner practitioner = practitioners.get(role);
		if (practitioner instanceof Doctor)
			return doctorService.viewPatientRecord(patientIdentificationNumber);
		if (practitioner instanceof Pharmacist)
			return pharmacistService.viewPatientRecord(patientIdentificationNumber);
		return GetRecordResponse.builder().build();
	}
	
	@Override
	public MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date) {
		return null;
	}
	
	@Override
	public TestRecommendationResponse recommendTest(TestRecommendationRequest testRecommendationRequest) {
		return null;
	}
	
	@Override
	public AppointmentScheduleResponse scheduleAppointment(AppointmentScheduleRequest appointmentScheduleRequest) {
		return null;
	}

	@Override
	public CloudUploadResponse uploadTestResult(CloudUploadRequest cloudUploadRequest) {
		return null;
	}

	@Override
	public ResponseEntity<?> uploadProfilePicture(MultipartFile multipartFile) throws EReachBaseException {
		String mediaUrl = pushToCloud(multipartFile);
		return new ResponseEntity<>(mediaUrl, HttpStatus.CREATED);
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
	public List<GetPractitionerResponse> getAllPractitionersInHospital(String hospitalEmail) {
		return null;
	}
	
	@Override
	public List<GetPractitionerResponse> getAllPractitioners() throws RequestInvalidException {
		List<Practitioner> foundPractitioners = practitionerRepository.findAll();
		if (foundPractitioners.isEmpty())
			throw new RequestInvalidException(NO_PRACTITIONER_FOUND);
		else return foundPractitioners.stream()
						              .map(practitioner -> mapper.map(practitioner, GetPractitionerResponse.class))
									  .toList();
	}
	
	@Override
	public void removeAll() throws RequestInvalidException {
		if (practitionerRepository.findAll().isEmpty())
			throw new RequestInvalidException(NO_PRACTITIONER_FOUND);
		else practitionerRepository.deleteAll();
	}
	
}

