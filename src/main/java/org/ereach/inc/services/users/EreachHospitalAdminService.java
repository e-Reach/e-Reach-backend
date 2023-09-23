package org.ereach.inc.services.users;

import jakarta.persistence.EnumType;
import lombok.AllArgsConstructor;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.InvitePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.data.repositories.hospital.EReachHospitalRepository;
import org.ereach.inc.data.repositories.users.HospitalAdminRepository;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.InMemoryDatabase;
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.services.notifications.EReachNotificationResponse;
import org.ereach.inc.services.notifications.MailService;
import org.ereach.inc.services.validators.EmailValidator;
import org.ereach.inc.utilities.JWTUtil;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.JWTUtil.extractEmailFrom;

@Service
@AllArgsConstructor
public class EreachHospitalAdminService implements HospitalAdminService {
	
	private EReachHospitalRepository hospitalRepository;
	private ModelMapper modelMapper;
	private InMemoryDatabase inMemoryDatabase;
	private HospitalService hospitalService;
	private final EReachConfig config;
	private HospitalAdminRepository hospitalAdminRepository;
	private MailService mailService;
	private EmailValidator validator;
	
	@Override
	public HospitalResponse registerHospital(@NotNull CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException {
		return hospitalService.registerHospital(hospitalRequest);
	}
	
	@Override
	public HospitalResponse saveHospitalAdminPermanently(String token) throws RequestInvalidException {
		if (JWTUtil.isValidToken(token, config.getAppJWTSecret())) {
			return activateAccount(token);
		}
		throw new RequestInvalidException("Request failed");
	}
	
	private HospitalResponse activateAccount(String token) {
		if (Objects.equals(token, config.getTestToken()))
			return activateTestAccount();
		String email = extractEmailFrom(token);
		HospitalAdmin hospitalAdmin = inMemoryDatabase.getTemporarilySavedHospitalAdmin(email);
		HospitalAdmin savedHospitalAdmin = hospitalAdminRepository.save(hospitalAdmin);
		Hospital foundHospital = inMemoryDatabase.findSavedAndActivatedHospitalByAdminEmail(savedHospitalAdmin.getAdminEmail());
		foundHospital.setAdmins(new HashSet<>());
		foundHospital.getAdmins().add(savedHospitalAdmin);
		hospitalRepository.save(foundHospital);
		return modelMapper.map(foundHospital, HospitalResponse.class);
	}
	
	private HospitalResponse activateTestAccount() {
		return HospitalResponse.builder()
				       .hospitalName(TEST_HOSPITAL_NAME)
				       .hospitalEmail(TEST_HOSPITAL_MAIL)
				       .build();
	}
	
	@Override
	public ResponseEntity<?> invitePractitioner(InvitePractitionerRequest practitionerRequest) {
		validator.validateEmail(practitionerRequest.getEmail());
		verifyRole(practitionerRequest);
		String fullName = practitionerRequest.getFirstName() + practitionerRequest.getLastName();
		return mailService.sendMail(practitionerRequest.getEmail(), practitionerRequest.getRole(), fullName, PRACTITIONER_ACCOUNT_ACTIVATION_MAIL_PATH);
	}
	
	private static void verifyRole(InvitePractitionerRequest practitionerRequest) {
		boolean isValidRole = false;
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
		return null;
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
