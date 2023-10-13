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
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.services.validators.EmailValidator;
import org.ereach.inc.utilities.JWTUtil;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.ereach.inc.utilities.Constants.*;

@Service
@AllArgsConstructor
@Slf4j
public class EreachHospitalAdminService implements HospitalAdminService {
	
	private final EReachHospitalRepository hospitalRepository;
	private final ModelMapper modelMapper;
	private final HospitalService hospitalService;
	private PatientService patientService;
	private final EReachConfig config;
	private final HospitalAdminRepository hospitalAdminRepository;
	private final EmailValidator validator;
	private final PractitionerService practitionerService;
	
	@Override
	public HospitalResponse registerHospital(@NotNull CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException {
		return hospitalService.registerHospital(hospitalRequest);
	}
	
	@Override
	public HospitalAdminResponse saveHospitalAdminPermanently(String token) throws RequestInvalidException {
		if (Objects.equals(token, config.getTestToken()))
			return activateTestAccount();
		else if (JWTUtil.isValidToken(token, config.getAppJWTSecret()))
			return activateTestAccount();
		else throw new RequestInvalidException("Request failed::Token "+token+" to activate hospital admin account was Invalid or has expired");
	}
	
	@Override
	public PractitionerResponse invitePractitioner(@NotNull InvitePractitionerRequest practitionerRequest) throws FieldInvalidException, RegistrationFailedException {
		validator.validateEmail(practitionerRequest.getEmail());
		validator.validateEmail(practitionerRequest.getHospitalEmail());
		verifyRole(practitionerRequest);
		return practitionerService.invitePractitioner(practitionerRequest);
	}
	
	@Override
	public CreatePatientResponse registerPatient(CreatePatientRequest createPatientRequest) throws EReachBaseException {
		return patientService.createPatient(createPatientRequest);
	}
	
	private HospitalAdminResponse activateTestAccount() {
		return HospitalAdminResponse.builder()
				       .adminLastName(TEST_HOSPITAL_NAME)
				       .adminFirstName(TEST_HOSPITAL_NAME)
				       .adminEmail(TEST_HOSPITAL_MAIL)
				       .build();
	}

	private static void verifyRole(InvitePractitionerRequest practitionerRequest) throws FieldInvalidException {
		System.out.println("i am here ");
		System.out.println(practitionerRequest.getRole().toUpperCase());
		if (EnumSet.allOf(Role.class)
				   .stream()
				   .noneMatch(role -> role == Role.valueOf(practitionerRequest.getRole().toUpperCase())))
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
				} else throw new EReachUncheckedBaseException(String.format(ADMIN_WITH_ID_DOES_NOT_EXIST_IN_HOSPITAL, id, hospitalEmail));
			}).orElseThrow(() -> new EReachUncheckedBaseException(String.format(ADMIN_WITH_ID_DOES_NOT_EXIST, id))));
		});
		return response.get();
	}
	
	@Override
	public GetHospitalAdminResponse findAdminByEmail(String email, String hospitalEmail) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		return foundHospital.map(hospital -> {
			HospitalAdmin foundAdmin = hospital.getAdmins().stream()
					                           .filter(admin -> Objects.equals(admin.getAdminEmail(), email))
					                           .findFirst()
					                           .orElseThrow(() -> new EReachUncheckedBaseException(
							                           String.format(ADMIN_WITH_EMAIL_DOES_NOT_EXIST_IN_HOSPITAL, email, hospitalEmail)
					                           ));
			
			return modelMapper.map(foundAdmin, GetHospitalAdminResponse.class);
		}).orElse(null);
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
	
	@Override
	public void deleteByEmail(String adminEmail) {
		hospitalAdminRepository.deleteByAdminEmail(adminEmail);
	}
	
}
/*
Optional<HospitalAdmin> foundAdmin = hospitalAdminRepository.findByAdminEmail(email);
* foundAdmin.map(hospitalAdmin -> {
							}).orElseThrow(() -> new EReachUncheckedBaseException(String.format(ADMIN_WITH_ID_DOES_NOT_EXIST, email))));
		}*/