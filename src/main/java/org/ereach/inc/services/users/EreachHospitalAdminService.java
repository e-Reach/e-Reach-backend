package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.request.InvitePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.data.repositories.hospital.EReachHospitalRepository;
import org.ereach.inc.data.repositories.users.HospitalAdminRepository;
import org.ereach.inc.exceptions.*;
import org.ereach.inc.services.InMemoryDatabase;
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.services.notifications.EReachNotificationRequest;
import org.ereach.inc.services.notifications.MailService;
import org.ereach.inc.services.validators.EmailValidator;
import org.ereach.inc.utilities.JWTUtil;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.JWTUtil.extractEmailFrom;

@Service
@AllArgsConstructor
@Slf4j
public class EreachHospitalAdminService implements HospitalAdminService {
	
	private final EReachHospitalRepository hospitalRepository;
	private final ModelMapper modelMapper;
	private final InMemoryDatabase inMemoryDatabase;
	private final HospitalService hospitalService;
	private PatientService patientService;
	private final EReachConfig config;
	private final HospitalAdminRepository hospitalAdminRepository;
	private final MailService mailService;
	private final EmailValidator validator;
	private final PractitionerService practitionerService;
	
	@Override
	public HospitalResponse registerHospital(@NotNull CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException {
		return hospitalService.registerHospital(hospitalRequest);
	}
	
	@Override
	public HospitalAdminResponse saveHospitalAdminPermanently(String token) throws RequestInvalidException {
		//if (Objects.equals(token, config.getTestToken()))
		//			return activateTestAccount();
		//else
		if (JWTUtil.isValidToken(token, config.getAppJWTSecret()))
			return activateAccount(token);
		else throw new RequestInvalidException("Request failed");
	}
	
	@Override
	public CreatePatientResponse registerPatient(CreatePatientRequest createPatientRequest) throws EReachBaseException {
		return patientService.createPatient(createPatientRequest);
	}
	private HospitalAdminResponse activateAccount(String token) throws RequestInvalidException {
		String email = extractEmailFrom(token);
		HospitalAdmin hospitalAdmin = inMemoryDatabase.retrieveAdminFromInMemory(email);
	
		HospitalAdmin savedHospitalAdmin = hospitalAdminRepository.save(hospitalAdmin);
		log.info("hospital admin in activate account after saving: {}", hospitalAdmin);
		Hospital foundHospital = inMemoryDatabase.findSavedAndActivatedHospitalByAdminEmail(savedHospitalAdmin.getAdminEmail());
		log.info("found saved and activated hospital in activate account: {}", foundHospital.toString());
		foundHospital.getAdmins().add(savedHospitalAdmin);
		log.info("found (under) saved and activated hospital in activate account: {}", foundHospital.toString());
		hospitalRepository.save(foundHospital);
		return modelMapper.map(savedHospitalAdmin, HospitalAdminResponse.class);
	}                                      
	
	private HospitalAdminResponse activateTestAccount() {
		return HospitalAdminResponse.builder()
				       .adminLastName(TEST_HOSPITAL_NAME)
				       .adminFirstName(TEST_HOSPITAL_NAME)
				       .adminEmail(TEST_HOSPITAL_MAIL)
				       .build();
	}
	
	@Override
	public PractitionerResponse invitePractitioner(@NotNull InvitePractitionerRequest practitionerRequest) throws FieldInvalidException, RegistrationFailedException {
		verifyRole(practitionerRequest);
		return practitionerService.invitePractitioner(practitionerRequest);
	}
	
	private EReachNotificationRequest buildNotificationRequest(@NotNull InvitePractitionerRequest practitionerRequest) {
		return EReachNotificationRequest.builder()
								       .firstName(practitionerRequest.getFirstName())
								       .lastName(practitionerRequest.getLastName())
								       .templatePath(PRACTITIONER_ACCOUNT_ACTIVATION_MAIL_PATH)
								       .email(practitionerRequest.getEmail())
								       .role(practitionerRequest.getRole())
								       .build();
	}

	private static void verifyRole(InvitePractitionerRequest practitionerRequest) throws FieldInvalidException {
		if (EnumSet.allOf(Role.class)
				   .stream()
				   .noneMatch(role ->role ==Role.valueOf(practitionerRequest.getRole().toUpperCase())))
			throw new FieldInvalidException("Invalid Role");
	}
	
	@Override
	public HospitalResponse editHospitalProfile(UpdateHospitalRequest hospitalRequest) {
		return hospitalService.editHospitalProfile(hospitalRequest);
	}
	
	@Override
	public GetHospitalAdminResponse findAdminById(String id, String hospitalEmail) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		AtomicReference<GetHospitalAdminResponse> atomicReference = new AtomicReference<>();
		AtomicReference<GetHospitalAdminResponse> response = new AtomicReference<>();
		foundHospital.ifPresent(hospital -> {
			Optional<HospitalAdmin> foundAdmin = hospitalAdminRepository.findById(id);
			response.set(foundAdmin.map(hospitalAdmin -> {
				if (hospital.getAdmins().stream().anyMatch(admin -> admin == hospitalAdmin)) {
					GetHospitalAdminResponse mappedResponse = modelMapper.map(foundAdmin, GetHospitalAdminResponse.class);
					atomicReference.set(mappedResponse);
					return atomicReference.get();
				} else
					throw new EReachUncheckedBaseException(String.format(ADMIN_WITH_ID_DOES_NOT_EXIST_IN_HOSPITAL, id, hospitalEmail));
			}).orElseThrow(() -> new EReachUncheckedBaseException(String.format(ADMIN_WITH_ID_DOES_NOT_EXIST, id))));
		});
		return response.get();
	}
	
	@Override
	public GetHospitalAdminResponse findAdminByEmail(String email, String hospitalEmail) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		AtomicReference<GetHospitalAdminResponse> atomicReference = new AtomicReference<>();
		AtomicReference<GetHospitalAdminResponse> response = new AtomicReference<>();
		foundHospital.ifPresent(hospital -> {
			Optional<HospitalAdmin> foundAdmin = hospitalAdminRepository.findById(email);
			response.set(foundAdmin.map(hospitalAdmin -> {
				if (hospital.getAdmins().stream().anyMatch(admin -> admin == hospitalAdmin)) {
					GetHospitalAdminResponse mappedResponse = modelMapper.map(foundAdmin, GetHospitalAdminResponse.class);
					atomicReference.set(mappedResponse);
					return atomicReference.get();
				} else
					throw new EReachUncheckedBaseException(String.format(ADMIN_WITH_ID_DOES_NOT_EXIST_IN_HOSPITAL, email, hospitalEmail));
			}).orElseThrow(() -> new EReachUncheckedBaseException(String.format(ADMIN_WITH_ID_DOES_NOT_EXIST, email))));
		});
		return response.get();
	}
	
	@Override
	public PractitionerResponse removePractitioner(String email) {
		
		return null;
	}
	
	@Override
	public CreatePatientResponse removePatient(String patientId, String hospitalHefamaaId) {
		return null;
	}
	
	@Override
	public List<PractitionerResponse> viewAllPractitioners(String hospitalHefamaaId) {
		return null;
	}
	
	@Override
	public List<GetRecordResponse> viewAllRecordsCreatedByHospital(String hefamaaId) {
		return null;
	}
	
	@Override
	public void importPatientDetails() {
	
	}
	
	@Override
	public HospitalResponse getHospitalRegisteredWith(String officerIdentificationNumber) {
		return null;
	}
	
}
