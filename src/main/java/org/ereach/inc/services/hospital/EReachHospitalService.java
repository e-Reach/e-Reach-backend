package org.ereach.inc.services.hospital;

import lombok.AllArgsConstructor;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.AddressCreationRequest;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.AddressResponse;
import org.ereach.inc.data.dtos.response.GetHospitalAdminResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.data.repositories.hospital.EReachHospitalRepository;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RequestInvalidException;
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
import java.util.concurrent.atomic.AtomicReference;

import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.JWTUtil.extractEmailFrom;

@Service
@AllArgsConstructor
public class EReachHospitalService implements HospitalService {
	
	private EReachHospitalRepository hospitalRepository;
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
	
	@Override
	public List<GetHospitalAdminResponse> findAllAdminByHefamaaId(String hospitalHefamaaId) {
		return null;
	}
	
	@Override
	public List<GetHospitalAdminResponse> findAllAdminByHospitalEmail(String hospitalEmail) {
		return null;
	}
	
	private HospitalResponse activateAccount(String token){
		String email = extractEmailFrom(token);
		Hospital hospital = inMemoryDatabase.getTemporarilySavedHospital(email);
		Hospital savedHospital = hospitalRepository.save(hospital);
		Optional<HospitalAdmin> foundAdmin = savedHospital.getAdmins().stream().findFirst();
		foundAdmin.ifPresent(admin -> {
			String adminFullName = admin.getAdminFirstName() + SPACE + admin.getAdminLastName();
			mailService.sendMail(admin.getAdminEmail(), admin.getId(), adminFullName, HOSPITAL_ACCOUNT_ACTIVATION_MAIL_PATH);
			inMemoryDatabase.saveHospitalAdminTemporarily(admin);
		});
		return modelMapper.map(savedHospital, HospitalResponse.class);
	}
	
	private HospitalResponse activateTestAccount() {
		return HospitalResponse.builder()
				       .hospitalName(TEST_HOSPITAL_NAME)
				       .hospitalEmail(TEST_HOSPITAL_MAIL)
				       .build();
	}
	
	@Override
	public HospitalResponse editHospitalProfile(UpdateHospitalRequest hospitalRequest) {
		return null;
	}
	
	@Override
	public HospitalResponse viewHospitalProfileByEmailOrHefamaaId(String email, String hefamaaId) {
		return null;
	}
	
	@Override
	public List<HospitalResponse> getAllHospitals() {
		return null;
	}
	
	@Override
	public HospitalResponse findHospitalById(String id) {
		Optional<Hospital> foundHospital = hospitalRepository.findById(id);
		AtomicReference<HospitalResponse> atomicReference = new AtomicReference<>();
		return foundHospital.map(hospital -> {
			HospitalResponse mappedResponse = modelMapper.map(foundHospital, HospitalResponse.class);
			atomicReference.set(mappedResponse);
			return atomicReference.get();
		}).orElseThrow(()-> new EReachUncheckedBaseException(String.format(HOSPITAL_WITH_ID_DOES_NOT_EXIST,id)));
	}
	
	@Override
	public HospitalResponse findHospitalByEmail(String email) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(email);
		AtomicReference<HospitalResponse> atomicReference = new AtomicReference<>();
		return foundHospital.map(hospital -> {
			HospitalResponse mappedResponse = modelMapper.map(foundHospital, HospitalResponse.class);
			atomicReference.set(mappedResponse);
			return atomicReference.get();
		}).orElseThrow(()-> new EReachUncheckedBaseException(String.format(HOSPITAL_WITH_EMAIL_DOES_NOT_EXIST,email)));
	}
	
	@Override
	public HospitalResponse findHospitalByHefamaaId(String hefamaa_Id) {
		return null;
	}
}
