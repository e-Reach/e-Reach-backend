package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.AddressCreationRequest;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.data.repositories.hospital.EReachHospitalRepository;
import org.ereach.inc.data.repositories.users.HospitalAdminRepository;
import org.ereach.inc.exceptions.*;
import org.ereach.inc.services.AddressService;
import org.ereach.inc.services.InMemoryDatabase;
import org.ereach.inc.services.notifications.MailService;
import org.ereach.inc.services.validators.EmailValidator;
import org.ereach.inc.services.validators.PasswordValidator;
import org.ereach.inc.utilities.JWTUtil;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.JWTUtil.extractEmailFrom;

@Service
@AllArgsConstructor
public class EreachHospitalAdminService implements HospitalAdminService {
	
	private EReachHospitalRepository hospitalRepository;
	private HospitalAdminRepository hospitalAdminRepository;
	private ModelMapper modelMapper;
	private MailService mailService;
	private EmailValidator emailValidator;
	private PasswordValidator passwordValidator;
	private AddressService addressService;
	private InMemoryDatabase inMemoryDatabase;
	private final EReachConfig config;
	
	@Override
	public HospitalResponse registerHospital(@NotNull CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException {
		emailValidator.validateEmail(hospitalRequest.getHospitalEmail());
		verifyHefamaaId(hospitalRequest.getHEFAMAA_ID());
		
		AddressCreationRequest mappedAddress = modelMapper.map(hospitalRequest, AddressCreationRequest.class);
		AddressResponse saveAddressResponse = addressService.saveAddress(mappedAddress);
		Address savedAddress = modelMapper.map(saveAddressResponse, Address.class);
		
		Hospital mappedHospital = modelMapper.map(hospitalRequest, Hospital.class);
		mappedHospital.setAddress(savedAddress);
		mappedHospital.setAdmins(new HashSet<>());
		mappedHospital.setPractitioners(new HashSet<>());
		mappedHospital.setRecords(new HashSet<>());
		
		HospitalAdmin admin = modelMapper.map(hospitalRequest, HospitalAdmin.class);
		mappedHospital.getAdmins().add(admin);
		
		Hospital temporarilySavedHospital = inMemoryDatabase.saveHospitalTemporarily(mappedHospital);
		mailService.sendMail(temporarilySavedHospital.getHospitalEmail(), temporarilySavedHospital.getId(),
				temporarilySavedHospital.getHospitalName(),HOSPITAL_ACCOUNT_ACTIVATION_MAIL_PATH);
		return modelMapper.map(temporarilySavedHospital, HospitalResponse.class);
	}
	
	private void verifyHefamaaId(String hefamaaId) {
	
	}
	
	public HospitalResponse saveHospitalPermanently(String token) throws RequestInvalidException {
		if (Objects.equals(token, config.getTestToken()))
			return activateTestAccount();
		else if (JWTUtil.isValidToken(token, config.getAppJWTSecret())) {
			return activateAccount(token);
		}
		throw new RequestInvalidException("Request failed: i no even know wetin to write");
	}
	
	private HospitalResponse activateAccount(String token){
		String email = extractEmailFrom(token);
		Hospital hospital = inMemoryDatabase.getTemporarilySavedHospital(email);
		Set<HospitalAdmin> admins = hospital.getAdmins();
		List<HospitalAdmin> savedAdmins = hospitalAdminRepository.saveAll(admins);
		hospital.getAdmins().addAll(savedAdmins);
		Hospital savedHospital = hospitalRepository.save(hospital);
		return modelMapper.map(hospital, HospitalResponse.class);
	}
	
	private HospitalResponse activateTestAccount() {
		return HospitalResponse.builder()
						       .hospitalName(TEST_HOSPITAL_NAME)
						       .hospitalEmail(TEST_HOSPITAL_MAIL)
						       .build();
	}
	
	@Override
	public PractitionerResponse invitePractitioner(CreatePractitionerRequest practitionerRequest) {
		return null;
	}
	
	@Override
	public HospitalResponse editHospitalProfile(UpdateHospitalRequest hospitalRequest) {
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
