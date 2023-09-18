package org.ereach.inc.services.users;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.data.dtos.response.UpdateEntryResponse;
import org.ereach.inc.data.repositories.users.EReachPractitionerRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EReachPharmacistService implements PharmacistService{
	private EReachPractitionerRepository practitionerRepository;
	@Override
	public PractitionerResponse createPharmacist(CreatePractitionerRequest practitionerRequest) {
		return null;
	}
	
	@Override
	public AddMedicationResponse addMedication(AddMedicationRequest addMedicationRequest) {
		return null;
	}
	
	@Override
	public GetRecordResponse viewPatientRecord(String patientIdentificationNumber) {
		return null;
	}
	
	@Override
	public UpdateEntryResponse editEntry(UpdateEntryRequest updateEntryRequest) {
		return null;
	}
}
