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
	private static final Map<String, Practitioner> practitioners = Map.of("doctor", new Doctor(),
																		  "labtechnician", new LabTechnician(),
																		  "pharmacist", new Pharmacist());
	@Override
	public PractitionerResponse invitePractitioner(InvitePractitionerRequest registerDoctorRequest) throws RegistrationFailedException {
		log.info("logs {}", registerDoctorRequest);
		try {
			PractitionerResponse response;
			if (registerDoctorRequest.getRole().equalsIgnoreCase("doctor")) {
				Doctor mappedDoctor = mapper.map(registerDoctorRequest, Doctor.class);
				buildPractitioner(mappedDoctor, DOCTOR);
				Doctor savedDoctor = practitionerRepository.save(mappedDoctor);
				hospitalService.addPractitioners(registerDoctorRequest.getHospitalEmail(), savedDoctor);
				response = mapper.map(savedDoctor, PractitionerResponse.class);
				log.info("{}", response);
			}
			else if ((registerDoctorRequest.getRole().equalsIgnoreCase("pharmacist"))){
				Pharmacist mappedPharmacist = mapper.map(registerDoctorRequest, Pharmacist.class);
				buildPractitioner(mappedPharmacist, PHARMACIST);
				Pharmacist savedPharmacist = practitionerRepository.save(mappedPharmacist);
				hospitalService.addPractitioners(registerDoctorRequest.getHospitalEmail(), savedPharmacist);
				response = mapper.map(savedPharmacist, PractitionerResponse.class);
				
			} else {
				LabTechnician mappedLabTechnician = mapper.map(registerDoctorRequest, LabTechnician.class);
				buildPractitioner(mappedLabTechnician, LAB_TECHNICIAN);
				LabTechnician savedLabTechnician = practitionerRepository.save(mappedLabTechnician);
				hospitalService.addPractitioners(registerDoctorRequest.getHospitalEmail(), savedLabTechnician);
				response = mapper.map(savedLabTechnician, PractitionerResponse.class);
			}
			EReachNotificationRequest notificationRequest = buildNotificationRequest(registerDoctorRequest);
			notificationRequest.setPassword(response.getPractitionerIdentificationNumber());
			System.out.println("notification request password:: "+notificationRequest.getPassword());
			mailService.sendPractitionerMail(notificationRequest);
			response.setMessage(ACCOUNT_ACTIVATION_SUCCESSFUL);
			return response;
		}catch (Throwable throwable){
			log.info("error at practitioner ==> ", throwable);
			throw new RegistrationFailedException(throwable);
		}
	}
	
	
	private static void buildPractitioner(Practitioner mappedPractitioner, Role role) {
		String fullName = mappedPractitioner.getFirstName() + SPACE + mappedPractitioner.getLastName();
		mappedPractitioner.setActive(true);
		mappedPractitioner.setStatus(ACTIVE);
		mappedPractitioner.setPractitionerIdentificationNumber(generateUniquePIN(fullName, mappedPractitioner.getEmail()));
		mappedPractitioner.setUserRole(role);
	}
	
	public PractitionerResponse activatePractitioner(String token) throws RequestInvalidException {
		if (isValidToken(token, config.getAppJWTSecret())){
			String practitionerEmail = extractEmailFrom(token);
			Optional<Practitioner> foundPractitioner = practitionerRepository.findByEmail(practitionerEmail);
			if (foundPractitioner.isPresent()){
				foundPractitioner.get().setActive(true);
				Practitioner activePractitioner = practitionerRepository.save(foundPractitioner.get());
				PractitionerResponse practitionerResponse = mapper.map(activePractitioner, PractitionerResponse.class);
				practitionerResponse.setMessage(ACCOUNT_ACTIVATION_SUCCESSFUL);
				return practitionerResponse;
			}
			throw new RequestInvalidException("Practitioner with email "+practitionerEmail+" does not exist");
		}
		throw new RequestInvalidException(String.format(TOKEN_WAS_INVALID, PRACTITIONER));
	}
	
	private EReachNotificationRequest buildNotificationRequest(@NotNull InvitePractitionerRequest practitionerRequest) {
		String url = FRONTEND_LOCALHOST_BASE_URL+"practitioner-login/";
		return EReachNotificationRequest.builder()
				       .firstName(practitionerRequest.getFirstName())
				       .lastName(practitionerRequest.getLastName())
				       .templatePath(PRACTITIONER_ACCOUNT_ACTIVATION_MAIL_PATH)
				       .url(url)
				       .email(practitionerRequest.getEmail())
				       .role(practitionerRequest.getRole())
				       .build();
	}
	
	@Override
	public PractitionerLoginResponse login(PractitionerLoginRequest loginRequest) throws EReachBaseException {
		Optional<Practitioner> foundPractitioner = practitionerRepository.findByEmail(loginRequest.getEmail());
		if (foundPractitioner.isPresent()){
			Practitioner practitioner = foundPractitioner.get();
			return PractitionerLoginResponse.builder()
					       .practitionerIdentificationNumber(loginRequest.getPractitionerIdentificationNumber())
					       .message("Login Successful")
					       .email(practitioner.getEmail())
					       .firstName(practitioner.getFirstName())
					       .lastName(practitioner.getLastName())
					       .phoneNumber(practitioner.getPhoneNumber())
					       .role(practitioner.getUserRole().toString())
					       .practitionerIdentificationNumber(practitioner.getPractitionerIdentificationNumber())
					       .build();
		}
		throw new EReachBaseException("Login Failed");
	}
	

	private EReachNotificationRequest buildNotificationRequest(Practitioner practitioner) {
		String url = "http://localhost:3000/practitioner-login/"+practitioner.getUserRole().toString().toLowerCase();
		return EReachNotificationRequest.builder()
								        .firstName(practitioner.getFirstName())
								        .lastName("successfully")
								        .templatePath(PRACTITIONER_ACCOUNT_ACTIVATION_MAIL_PATH)
				                        .url(url)
								        .email(practitioner.getEmail())
								        .username(practitioner.getPractitionerIdentificationNumber())
								        .build();
	}
	
//	private static void buildPractitioner(Practitioner mappedPractitioner, Role role) {
//		String fullName = mappedPractitioner.getFirstName() + SPACE + mappedPractitioner.getLastName();
//		mappedPractitioner.setActive(true);
//		mappedPractitioner.setStatus(ACTIVE);
//		mappedPractitioner.setPractitionerIdentificationNumber(generateUniquePIN(fullName, mappedPractitioner.getEmail()));
//		mappedPractitioner.setUserRole(role);
//	}
//
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
		if (role.equalsIgnoreCase("doctor"))
			return doctorService.viewPatientRecord(patientIdentificationNumber);
		else if (role.equalsIgnoreCase("pharmacist"))
			return pharmacistService.viewPatientRecord(patientIdentificationNumber);
		else if (role.equalsIgnoreCase("labtechnician"))
			return null;
		return GetRecordResponse.builder().build();
	}

	@Override
	public List<GetRecordResponse> viewPatientsRecords(String hospitalEmail, String role) {
		if (role.equalsIgnoreCase("doctor"))
			return hospitalService.viewPatientsRecords(hospitalEmail);
		return null;
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

