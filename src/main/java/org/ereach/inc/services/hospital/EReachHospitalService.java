package org.ereach.inc.services.hospital;

import lombok.AllArgsConstructor;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.AddressCreationRequest;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.AddressResponse;
import org.ereach.inc.data.dtos.response.GetHospitalAdminResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.data.models.users.Practitioner;
import org.ereach.inc.data.repositories.hospital.EReachHospitalRepository;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.AddressService;
import org.ereach.inc.services.InMemoryDatabase;
import org.ereach.inc.services.notifications.EReachNotificationRequest;
import org.ereach.inc.services.notifications.MailService;
import org.ereach.inc.services.validators.EmailValidator;
import org.ereach.inc.services.validators.PasswordValidator;
import org.ereach.inc.utilities.JWTUtil;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.ereach.inc.data.models.Role.HOSPITAL_ADMIN;
import static org.ereach.inc.data.models.Role.HOSPITAL;
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
		mappedHospital.setRole(HOSPITAL);
		mappedHospital.setAddress(savedAddress);
		mappedHospital.setLogsCreated(new HashSet<>());
		mappedHospital.setAdmins(new HashSet<>());
		mappedHospital.setPractitioners(new HashSet<>());
		mappedHospital.setRecords(new HashSet<>());
		
		HospitalAdmin admin = modelMapper.map(hospitalRequest, HospitalAdmin.class);
		admin.setId(null);
		admin.setAdminRole(HOSPITAL_ADMIN);
		mappedHospital.getAdmins().add(admin);
		
		Hospital temporarilySavedHospital = inMemoryDatabase.temporarySave(mappedHospital);
		mailService.sendMail(buildNotificationRequest(temporarilySavedHospital));
		return modelMapper.map(temporarilySavedHospital, HospitalResponse.class);
	}
	
	private static EReachNotificationRequest buildNotificationRequest(Hospital hospital) {
		return EReachNotificationRequest.builder()
				       .firstName(hospital.getHospitalName())
				       .templatePath(HOSPITAL_ACCOUNT_ACTIVATION_MAIL_PATH)
				       .email(hospital.getHospitalEmail())
				       .role(hospital.getRole().toString())
				       .build();
	}
	private void verifyHefamaaId(String hefamaaId) {
	
	}
	
	public HospitalResponse saveHospitalPermanently(String token) throws RequestInvalidException {
		if (Objects.equals(token, config.getTestToken()))
			return activateTestAccount();
		else if (JWTUtil.isValidToken(token, config.getAppJWTSecret())) {
			return activateAccount(token);
		}
		throw new RequestInvalidException(String.format(TOKEN_WAS_INVALID, HOSPITAL));
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
		Hospital hospital = inMemoryDatabase.retrieveHospitalFromInMemory(email);
		Optional<HospitalAdmin> foundAdmin = hospital.getAdmins().stream().findFirst();
		AtomicReference<Hospital> savedHospital = new AtomicReference<>();
		foundAdmin.ifPresent(admin -> {
			try {
				mailService.sendMail(buildNotificationRequest(admin));
				admin.setAdminRole(HOSPITAL_ADMIN);
				inMemoryDatabase.temporarySave(admin);
				hospital.setAdmins(new HashSet<>());
				Hospital saved = hospitalRepository.save(hospital);
				savedHospital.set(saved);
				inMemoryDatabase.addHospitalToSavedHospitals(admin.getAdminEmail(), hospital);
			} catch (RequestInvalidException e) {
				throw new EReachUncheckedBaseException(e);
			}
		});
		return modelMapper.map(savedHospital.get(), HospitalResponse.class);
	}
	
	private static EReachNotificationRequest buildNotificationRequest(HospitalAdmin admin) {
		return EReachNotificationRequest.builder()
				       .firstName(admin.getAdminFirstName())
				       .lastName(admin.getAdminLastName())
				       .templatePath(HOSPITAL_ACCOUNT_ACTIVATION_MAIL_PATH)
				       .email(admin.getAdminEmail())
				       .role(admin.getAdminRole().toString())
				       .build();
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
	
	@Override
	public void removeHospital(String mail) {
		hospitalRepository.deleteAll();
		hospitalRepository.deleteByHospitalEmail(mail);
	}
	
	@Override
	public void addToLog(String hospitalEmail, MedicalLog medicalLog) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		foundHospital.map(hospital->{
			hospital.getLogsCreated().add(medicalLog);
			hospitalRepository.save(hospital);
			return hospital;
		}).orElseThrow(()->new EReachUncheckedBaseException(String.format(HOSPITAL_WITH_EMAIL_DOES_NOT_EXIST, hospitalEmail)));
	}
	
	@Override
	public void addToRecords(String hospitalEmail, Record savedRecord) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		foundHospital.map(hospital->{
			hospital.getRecords().add(savedRecord);
			hospitalRepository.save(hospital);
			return hospital;
		}).orElseThrow(()->new EReachUncheckedBaseException(String.format(HOSPITAL_WITH_EMAIL_DOES_NOT_EXIST, hospitalEmail)));
		
	}
	
	@Override
	public void addPractitioners(String hospitalEmail, Practitioner savedPractitioner) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		foundHospital.map(hospital->{
			hospital.getPractitioners().add(savedPractitioner);
			hospitalRepository.save(hospital);
			return hospital;
		}).orElseThrow(()->new EReachUncheckedBaseException(String.format(HOSPITAL_WITH_EMAIL_DOES_NOT_EXIST, hospitalEmail)));
		
	}
	
	@Override
	public List<MedicalLogResponse> viewPatientsMedicalLogs(String hospitalEmail) {
		return null;
	}
}
