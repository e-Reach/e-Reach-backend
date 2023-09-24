package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.AppointmentScheduleRequest;
import org.ereach.inc.data.dtos.request.CloudUploadRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.TestRecommendationRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.models.AccountStatus;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.users.Doctor;
import org.ereach.inc.data.models.users.LabTechnician;
import org.ereach.inc.data.models.users.Pharmacist;
import org.ereach.inc.data.models.users.Practitioner;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.ereach.inc.utilities.Constants.*;
import static org.ereach.inc.utilities.JWTUtil.*;
import static org.ereach.inc.utilities.PractitionerIdentificationNumberGenerator.generateUniquePIN;

@Service
@AllArgsConstructor
public class EReachPractitionerService implements PractitionerService{
	
	private EReachConfig config;
	private ModelMapper mapper;
	private EReachPractitionerRepository practitionerRepository;
	private static final Map<String, Practitioner> practitioners = Map.of("doctor", new Doctor(),
																		  "labTechnician", new LabTechnician(),
																		  "pharmacist", new Pharmacist());
	@Override
	public PractitionerResponse activatePractitionerAccount(CreatePractitionerRequest registerDoctorRequest) {
		Practitioner practitioner = practitioners.get(registerDoctorRequest.getRole());
		Converter<String, Role> stringToRole = role -> Role.valueOf(registerDoctorRequest.getRole().toUpperCase());
		mapper.createTypeMap(CreatePractitionerRequest.class, Practitioner.class)
			  .addMappings(mapping -> mapping.using(stringToRole).map(CreatePractitionerRequest::getRole, Practitioner::setUserRole));
		
		Practitioner savedPractitioner;
		if (practitioner instanceof Doctor) {
			Doctor mappedDoctor = mapper.map(registerDoctorRequest, Doctor.class);
			String fullName = mappedDoctor.getFirstName() + SPACE + mappedDoctor.getLastName();
			mappedDoctor.setActive(true);
			mappedDoctor.setStatus(AccountStatus.ACTIVE);
			mappedDoctor.setPractitionerIdentificationNumber(generateUniquePIN(fullName, mappedDoctor.getPhoneNumber()));
			savedPractitioner = practitionerRepository.save(mappedDoctor);
		}
		else if (practitioner instanceof Pharmacist){
			Pharmacist mappedPharmacist = mapper.map(registerDoctorRequest, Pharmacist.class);
			String fullName = mappedPharmacist.getFirstName() + SPACE + mappedPharmacist.getLastName();
			mappedPharmacist.setActive(true);
			mappedPharmacist.setStatus(AccountStatus.ACTIVE);
			mappedPharmacist.setPractitionerIdentificationNumber(generateUniquePIN(fullName, mappedPharmacist.getPhoneNumber()));
			savedPractitioner = practitionerRepository.save(mappedPharmacist);
		}
		else{
			LabTechnician mappedLabTechnician = mapper.map(registerDoctorRequest, LabTechnician.class);
			String fullName = mappedLabTechnician.getFirstName() + SPACE + mappedLabTechnician.getLastName();
			mappedLabTechnician.setActive(true);
			mappedLabTechnician.setStatus(AccountStatus.ACTIVE);
			mappedLabTechnician.setPractitionerIdentificationNumber(generateUniquePIN(fullName, mappedLabTechnician.getPhoneNumber()));
			savedPractitioner = practitionerRepository.save(mappedLabTechnician);
		}
		return mapper.map(savedPractitioner, PractitionerResponse.class);
	}
	
	@Override
	public PractitionerResponse activatePractitionerAccount(String token) throws RequestInvalidException {
		if (isValidToken(token, config.getAppJWTSecret())){
			CreatePractitionerRequest createPractitionerRequest = buildPractitionerCreationRequest(token);
			return activatePractitionerAccount(createPractitionerRequest);
		}
		throw new RequestInvalidException(String.format(TOKEN_WAS_INVALID, PRACTITIONER));
	}
	
	private CreatePractitionerRequest buildPractitionerCreationRequest(String token) {
		return CreatePractitionerRequest.builder()
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
	public GetRecordResponse viewPatientRecord(String patientIdentificationNumber) {
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
	public List<GetPractitionerResponse> getAllPractitionersInHospital(String hospitalEmail) {
		return null;
	}
	
	@Override
	public List<GetPractitionerResponse> getAllPractitioners() {
		return null;
	}
	
}

