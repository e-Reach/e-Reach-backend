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
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.services.hospital.EreachMedicationService;
import org.springframework.stereotype.Service;
import static org.ereach.inc.utilities.Constants.*;

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
		if(practitionerRepository.existsByEmail(practitionerRequest.getEmail())) throw new RegistrationFailedException(EMAIL_ALREADY_EXIST);
		validatePhoneNumber(practitionerRequest.getPhoneNumber());
		validateName(practitionerRequest.getFirstName());
		validateName(practitionerRequest.getLastName());
		Practitioner builtPractitioner = mapFromRequestToPharmacist(practitionerRequest);
		practitionerRepository.save(builtPractitioner);
		return new PractitionerResponse(PHARMACIST_SUCCESSFULLY_CREATED);
	}
	private void validatePhoneNumber(String phoneNumber){
	if (phoneNumber.length() != 11) throw new FieldInvalidException(INCOMPLETE_DETAILS_PROVIDED);
	for (int i = 0; i < phoneNumber.length(); i++) {
		if (Character.isAlphabetic(phoneNumber.charAt(i))) throw new FieldInvalidException(FIELD_INVALID_EXCEPTIONS);
		}
	}
	private void validateName(String name){
		if (name.contains(" ")) throw new FieldInvalidException(INCOMPLETE_DETAILS_PROVIDED);
	}
	@Override
	public AddMedicationResponse addMedication(AddMedicationRequest addMedicationRequest) {
		medicationService.createMedication(addMedicationRequest);
		return new AddMedicationResponse(MEDICATION_ADDED_SUCCESSFULLY);
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
