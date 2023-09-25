package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.AppointmentScheduleRequest;
import org.ereach.inc.data.dtos.request.CloudUploadRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.TestRecommendationRequest;
import org.ereach.inc.data.dtos.response.*;

import static org.ereach.inc.data.models.AccountStatus.ACTIVE;
import static org.ereach.inc.data.models.Role.*;

import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.users.Doctor;
import org.ereach.inc.data.models.users.LabTechnician;
import org.ereach.inc.data.models.users.Pharmacist;
import org.ereach.inc.data.models.users.Practitioner;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.hospital.HospitalService;
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
	private DoctorService doctorService;
	private PharmacistService pharmacistService;
	private EReachPractitionerRepository practitionerRepository;
	private HospitalService hospitalService;
	private static final Map<String, Practitioner> practitioners = Map.of("doctor", new Doctor(),
																		  "labTechnician", new LabTechnician(),
																		  "pharmacist", new Pharmacist());
	@Override
	public PractitionerResponse activatePractitionerAccount(CreatePractitionerRequest registerDoctorRequest) throws RegistrationFailedException {
		Practitioner practitioner = practitioners.get(registerDoctorRequest.getRole());
		try {
			Practitioner savedPractitioner;
			if (practitioner instanceof Doctor) {
				Doctor mappedDoctor = mapper.map(registerDoctorRequest, Doctor.class);
				buildPractitioner(mappedDoctor, DOCTOR);
				savedPractitioner = practitionerRepository.save(mappedDoctor);
			} else if (practitioner instanceof Pharmacist) {
				Pharmacist mappedPharmacist = mapper.map(registerDoctorRequest, Pharmacist.class);
				buildPractitioner(mappedPharmacist, PHARMACIST);
				savedPractitioner = practitionerRepository.save(mappedPharmacist);
			} else {
				LabTechnician mappedLabTechnician = mapper.map(registerDoctorRequest, LabTechnician.class);
				buildPractitioner(mappedLabTechnician, LAB_TECHNICIAN);
				savedPractitioner = practitionerRepository.save(mappedLabTechnician);
			}
			hospitalService.addPractitioners(registerDoctorRequest.getHospitalEmail(), savedPractitioner);
			PractitionerResponse response = mapper.map(savedPractitioner, PractitionerResponse.class);
			response.setMessage(ACCOUNT_ACTIVATION_SUCCESSFUL);
			return response;
		}catch (Throwable throwable){
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
	
	@Override
	public PractitionerResponse activatePractitionerAccount(String token) throws RequestInvalidException, RegistrationFailedException {
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

