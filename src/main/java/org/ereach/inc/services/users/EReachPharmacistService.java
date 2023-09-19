package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.data.dtos.response.UpdateEntryResponse;
import org.ereach.inc.data.models.hospital.Medication;
import org.ereach.inc.data.models.users.Practitioner;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.services.hospital.EreachMedicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class EReachPharmacistService implements PharmacistService{
	private final EReachPractitionerRepository practitionerRepository;
	private final EreachMedicationService medicationService;
	@Override
	public PractitionerResponse createPharmacist(CreatePractitionerRequest practitionerRequest){
		if(practitionerRepository.existsByEmail(practitionerRequest.getEmail())) throw new RegistrationFailedException("Email already exists");
		Practitioner builtPractitioner = mapFromRequestToPharmacist(practitionerRequest);
		practitionerRepository.save(builtPractitioner);
		return new PractitionerResponse("\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13 successfully created");
	}
	
	@Override
	public AddMedicationResponse addMedication(AddMedicationRequest addMedicationRequest) {
		medicationService.createMedication(addMedicationRequest);
		return new AddMedicationResponse("Added Successfully");
	}
	
	@Override
	public GetRecordResponse viewPatientRecord(String patientIdentificationNumber) {
		return null;
	}
	
	@Override
	public UpdateEntryResponse editEntry(UpdateEntryRequest updateEntryRequest) {
		return null;
	}



	private Practitioner mapFromRequestToPharmacist(CreatePractitionerRequest practitionerRequest){
		return Practitioner.builder()
				.email(practitionerRequest.getEmail())
				.firstName(practitionerRequest.getFirstName())
				.lastName(practitionerRequest.getLastName())
				.phoneNumber(practitionerRequest.getPhoneNumber())
				.build();
	}

}
