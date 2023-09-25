package org.ereach.inc.services.users;

import lombok.RequiredArgsConstructor;
import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.data.dtos.response.UpdateEntryResponse;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.ereach.inc.services.hospital.EreachMedicationService;
import org.springframework.stereotype.Service;
import static org.ereach.inc.utilities.Constants.*;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EReachPharmacistService implements PharmacistService{
	private final EReachPractitionerRepository practitionerRepository;
	private final EreachMedicationService medicationService;


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
	
	@Override
	public void removePharmacistByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber) {
	
	}
}
