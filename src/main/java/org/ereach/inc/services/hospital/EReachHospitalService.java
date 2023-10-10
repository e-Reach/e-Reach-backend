package org.ereach.inc.services.hospital;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.AddressCreationRequest;
import org.ereach.inc.data.dtos.request.AddressUpdateRequest;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.dtos.response.entries.*;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.data.models.users.Practitioner;
import org.ereach.inc.data.repositories.hospital.EReachHospitalRepository;
import org.ereach.inc.data.repositories.users.HospitalAdminRepository;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.AddressService;
import org.ereach.inc.services.InMemoryDatabase;
import org.ereach.inc.services.notifications.EReachNotificationRequest;
import org.ereach.inc.services.notifications.MailService;
import org.ereach.inc.services.validators.EmailValidator;
import org.ereach.inc.utilities.JWTUtil;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.apache.catalina.util.Introspection.getDeclaredFields;
import static org.ereach.inc.data.models.Role.HOSPITAL;
import static org.ereach.inc.data.models.Role.HOSPITAL_ADMIN;
import static org.ereach.inc.utilities.AppUtil.doReplace;
import static org.ereach.inc.utilities.AppUtil.filterEmptyField;
import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.JWTUtil.extractEmailFrom;

@Service
@AllArgsConstructor
@Slf4j
public class EReachHospitalService implements HospitalService {


	private EReachHospitalRepository hospitalRepository;
	private ModelMapper modelMapper;
	private HospitalAdminRepository hospitalAdminRepository;
	private MailService mailService;
	private EmailValidator emailValidator;
	private AddressService addressService;
	private InMemoryDatabase inMemoryDatabase;
	private final EReachConfig config;
	private ObjectMapper objectMapper;


	@Override
	public HospitalResponse registerHospital(@NotNull CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException {
		emailValidator.validateEmail(hospitalRequest.getHospitalEmail());
		verifyHefamaaId(hospitalRequest.getHEFAMAA_ID());

		AddressCreationRequest mappedAddress = modelMapper.map(hospitalRequest, AddressCreationRequest.class);
		AddressResponse saveAddressResponse = addressService.saveAddress(mappedAddress);
		Address savedAddress = modelMapper.map(saveAddressResponse, Address.class);

		Hospital mappedHospital = modelMapper.map(hospitalRequest, Hospital.class);
		mappedHospital.setUserRole(HOSPITAL);
		mappedHospital.setAddress(savedAddress);
		mappedHospital.setLogsCreated(new HashSet<>());
		mappedHospital.setAdmins(new HashSet<>());
		mappedHospital.setPractitioners(new HashSet<>());
		mappedHospital.setRecords(new HashSet<>());

		HospitalAdmin admin = modelMapper.map(hospitalRequest, HospitalAdmin.class);
		admin.setAdminRole(HOSPITAL_ADMIN);
		mappedHospital.getAdmins().add(admin);
		hospitalRepository.save(mappedHospital);

		Hospital temporarilySavedHospital = inMemoryDatabase.temporarySave(mappedHospital);
		mailService.sendMail(buildNotificationRequest(temporarilySavedHospital));
		return modelMapper.map(temporarilySavedHospital, HospitalResponse.class);
	}

	private EReachNotificationRequest buildNotificationRequest(Hospital hospital) {
		return EReachNotificationRequest.builder()
				.firstName(hospital.getHospitalName())
				.templatePath(HOSPITAL_ACCOUNT_ACTIVATION_MAIL_PATH)
				.email(hospital.getHospitalEmail())
				.role(hospital.getUserRole().toString())
				.url(hospitalAccountActivationUrl(hospital.getHospitalEmail(), hospital.getUserRole().toString(), hospital.getHospitalName(), hospital.getHospitalName()))
				.build();
	}
	private void verifyHefamaaId(String hefamaaId) {

	}
	private String hospitalAccountActivationUrl(String email, String role, String firstName, String lastName){
		return ACTIVATE_HOSPITAL_ACCOUNT + JWTUtil.generateAccountActivationUrl(email, role, firstName, lastName,config.getAppJWTSecret());
	}
	public HospitalResponse saveHospitalPermanently(String token) throws EReachBaseException {
		if (Objects.equals(token, config.getTestToken()))
			return activateTestAccount();
		else if (JWTUtil.isValidToken(token, config.getAppJWTSecret()))
			return activateAccount(token);
		else throw new EReachBaseException("Token "+token+" to activate hospital admin account was Invalid or has expired");
	}

	@Override
	public List<GetHospitalAdminResponse> findAllAdminByHefamaaId(String hospitalHefamaaId) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalHefamaaId);
		return foundHospital.map(hospital -> hospital.getAdmins()
						.stream()
						.map(admin -> modelMapper.map(admin, GetHospitalAdminResponse.class))
						.toList())
				.orElseThrow(()->new EReachUncheckedBaseException(String.format(HOSPITAL_WITH_ID_DOES_NOT_EXIST, hospitalHefamaaId)));

	}

	@Override
	public List<GetHospitalAdminResponse> findAllAdminByHospitalEmail(String hospitalEmail) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		return foundHospital.map(hospital -> hospital.getAdmins()
						.stream()
						.map(admin -> modelMapper.map(admin, GetHospitalAdminResponse.class))
						.toList())
				.orElseThrow(()->new EReachUncheckedBaseException(String.format(HOSPITAL_WITH_EMAIL_DOES_NOT_EXIST, hospitalEmail)));
	}

	@Override
	public List<GetPractitionerResponse> getAllPractitioners(String hospitalEmail) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		return foundHospital.map(hospital -> hospital.getPractitioners()
						.stream()
						.map(practitioner -> modelMapper.map(practitioner, GetPractitionerResponse.class))
						.toList())
				.orElseThrow(()->new EReachUncheckedBaseException(String.format(HOSPITAL_WITH_EMAIL_DOES_NOT_EXIST, hospitalEmail)));
	}

	@Override
	public List<GetRecordResponse> getAllRecordsCreated(String hospitalEmail) throws EReachBaseException {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		if (foundHospital.isPresent()){
			Set<Record> hospitalRecords = foundHospital.get().getRecords();
			return hospitalRecords.stream()
					.map(record -> {
						Set<MedicalLog> logsCreated = foundHospital.get().getLogsCreated();
						List<MedicalLogResponse> logsResponses = logsCreated.stream().map(log -> {
							MedicalLogResponse response = new MedicalLogResponse();
							response.setDateCreated(log.getDateCreated());
							response.setHospitalEmail(hospitalEmail);
							response.setHospitalName(foundHospital.get().getHospitalName());
							response.setTimeCreated(log.getTimeCreated());
							response.setPrescriptionsResponseDTO(mapList(log.getPrescriptions(), PrescriptionsResponseDTO.class));
							response.setTestResponseDTO(mapList(log.getTests(), TestResponseDTO.class));
							response.setVitalsResponseDTO(modelMapper.map(log.getVitals(), VitalsResponseDTO.class));
							response.setDoctorReportResponseDTO(modelMapper.map(log.getDoctorsReport(), DoctorReportResponseDTO.class));
							return response;
						}).toList();
						GetRecordResponse records = modelMapper.map(record, GetRecordResponse.class);
						records.setMedicalLogResponses(logsResponses);
						records.setMessage("Record found");
						return records;
					})
					.toList();
		}
		throw new EReachBaseException(String.format(HOSPITAL_WITH_EMAIL_DOES_NOT_EXIST, hospitalEmail));
	}

	<S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
		return source.stream()
				.map(element -> modelMapper.map(element, targetClass))
				.collect(Collectors.toList());
	}

	private HospitalResponse activateAccount(String token){
		String email = extractEmailFrom(token);
		Hospital hospital = inMemoryDatabase.retrieveHospitalFromInMemory(email);
		Optional<HospitalAdmin> foundAdmin = hospital.getAdmins().stream().findFirst();
		AtomicReference<Hospital> savedHospitalReference = new AtomicReference<>();
		foundAdmin.ifPresent(admin -> {
			try {
				admin.setAdminRole(HOSPITAL_ADMIN);
				inMemoryDatabase.temporarySave(admin);
				hospital.setAdmins(new HashSet<>());
				Hospital savedHospital = hospitalRepository.save(hospital);
				savedHospitalReference.set(savedHospital);
				inMemoryDatabase.addHospitalToSavedHospitals(admin.getAdminEmail(), hospital);
				mailService.sendMail(buildNotificationRequest(admin));
			} catch (RequestInvalidException e) {
				throw new EReachUncheckedBaseException(e);
			}
		});
		return modelMapper.map(savedHospitalReference.get(), HospitalResponse.class);
	}

	private EReachNotificationRequest buildNotificationRequest(HospitalAdmin admin) {
		return EReachNotificationRequest.builder()
				.firstName(admin.getAdminFirstName())
				.lastName(admin.getAdminLastName())
				.templatePath(HOSPITAL_ACCOUNT_ACTIVATION_MAIL_PATH)
				.email(admin.getAdminEmail())
				.role(admin.getAdminRole().toString())
				.url(urlForHospitalAdmin(
						admin.getAdminEmail(),
						admin.getAdminRole().toString(),
						admin.getAdminFirstName(),
						admin.getAdminLastName()))
				.build();
	}

	private String urlForHospitalAdmin(String email, String role, String firstName, String lastName){
		return ACTIVATE_HOSPITAL_ADMIN_ACCOUNT + JWTUtil.generateAccountActivationUrl(email, role, firstName, lastName,config.getAppJWTSecret());
	}

	private HospitalResponse activateTestAccount() {
		return HospitalResponse.builder()
				.hospitalName(TEST_HOSPITAL_NAME)
				.hospitalEmail(TEST_HOSPITAL_MAIL)
				.build();
	}

	@Override
	public HospitalResponse editHospitalProfile(UpdateHospitalRequest hospitalRequest) {
		String logoUrl = scanForFile(hospitalRequest.getLogo());
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalRequest.getHospitalEmail());
		AtomicReference<HospitalResponse> atomicReference = new AtomicReference<>();
		foundHospital.ifPresentOrElse(hospital -> {
			JsonPatch updatePatch = buildHospitalUpdatePatch(hospitalRequest);
			try {
				HospitalResponse response = applyPatchTo(hospital, updatePatch);
				response.setLogoCloudUrl(logoUrl);
				atomicReference.set(response);
			} catch (JsonPatchException e) {
				throw new EReachUncheckedBaseException(e);
			}
		}, ()->{});
		return atomicReference.get();
	}

	private String scanForFile(MultipartFile logo) {
		if (!logo.isEmpty())
			return pushToCloud(logo);
		return null;
	}

	private String pushToCloud(MultipartFile logo) {
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
			throw new EReachUncheckedBaseException(exception+" File upload failed");
		}
	}

	private HospitalResponse applyPatchTo(Hospital hospital, JsonPatch updatePatch) throws JsonPatchException {
		JsonNode convertedAddress = objectMapper.convertValue(hospital, JsonNode.class);
		JsonNode updatedNode = updatePatch.apply(convertedAddress);
		hospital = objectMapper.convertValue(updatedNode, Hospital.class);
		hospitalRepository.save(hospital);
		return modelMapper.map(hospital, HospitalResponse.class) ;
	}

	private JsonPatch buildHospitalUpdatePatch(UpdateHospitalRequest hospitalRequest) {
		List<ReplaceOperation> operations = Arrays.stream(getDeclaredFields(AddressUpdateRequest.class))
				.filter(field -> filterEmptyField(hospitalRequest, field))
				.map(field -> doReplace(hospitalRequest, field))
				.toList();
		List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
		return new JsonPatch(patchOperations);
	}

	@Override
	public HospitalResponse viewHospitalProfileByEmailOrHefamaaId(String email, String hefamaaId) {
		return null;
	}

	@Override
	public List<HospitalResponse> getAllHospitals() {
		return hospitalRepository.findAll()
				.stream()
				.map(hospital -> modelMapper.map(hospital, HospitalResponse.class))
				.toList();
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
	public String removeHospital(String mail) throws RequestInvalidException {
		if (hospitalRepository.existsByHospitalEmail(mail))
			hospitalRepository.deleteByHospitalEmail(mail);
		throw new RequestInvalidException(String.format(HOSPITAL_WITH_EMAIL_DOES_NOT_EXIST, mail));
	}

	@Override
	public MedicalLogResponse addToLog(String hospitalEmail, MedicalLog medicalLog) {
		Optional<Hospital> foundHospital = hospitalRepository.findByHospitalEmail(hospitalEmail);
		return foundHospital.map(hospital->{
			hospital.getLogsCreated().add(medicalLog);
			Hospital savedHospital = hospitalRepository.save(hospital);
			return MedicalLogResponse.builder()
					.hospitalEmail(savedHospital.getHospitalEmail())
					.hospitalName(savedHospital.getHospitalName())
					.build();
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
		hospitalRepository.getReferenceById(hospitalEmail);

		return null;
	}
}