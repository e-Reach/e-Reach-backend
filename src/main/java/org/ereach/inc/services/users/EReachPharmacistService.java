package org.ereach.inc.services.users;

import lombok.RequiredArgsConstructor;
import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.data.dtos.response.UpdateEntryResponse;
import org.ereach.inc.data.models.users.Practitioner;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.services.hospital.EreachMedicationService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EReachPharmacistService implements PharmacistService{
	private final EReachPractitionerRepository practitionerRepository;
	private final EreachMedicationService medicationService;
	@Override
	public PractitionerResponse createPharmacist(CreatePractitionerRequest practitionerRequest) throws RegistrationFailedException, FieldInvalidException {
		if(practitionerRepository.existsByEmail(practitionerRequest.getEmail())) throw new RegistrationFailedException("Email already exists");
		validateName(practitionerRequest.getFirstName());
		validateName(practitionerRequest.getLastName());
		Practitioner builtPractitioner = mapFromRequestToPharmacist(practitionerRequest);
		practitionerRepository.save(builtPractitioner);
		return new PractitionerResponse("\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13\uD83E\uDD13 successfully created");
	}
	private void validatePhoneNumber(String phoneNumber) throws FieldInvalidException {
	if (phoneNumber.length() != 11) throw new FieldInvalidException("Incomplete details provided");
	for (int i = 0; i < phoneNumber.length(); i++) {
		if (Character.isAlphabetic(phoneNumber.charAt(i))) throw new FieldInvalidException("Phonenumber must not contain alphabet");
		}
	}
	private void validateName(String name) throws FieldInvalidException {
		if (name.contains(" ")) throw new FieldInvalidException("Incomplete details provided");
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
	
	@Override
	public void removePharmacistByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber) {
	
	}
	
	
	private Practitioner mapFromRequestToPharmacist(CreatePractitionerRequest practitionerRequest){
		return Practitioner.builder()
				.email(practitionerRequest.getEmail())
				.firstName(practitionerRequest.getFirstName())
				.lastName(practitionerRequest.getLastName())
				.build();
	}

}
