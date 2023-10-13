package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.RemovePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.data.dtos.response.UpdateEntryResponse;

public interface PharmacistService {
	PractitionerResponse createPharmacist(CreatePractitionerRequest practitionerRequest);

	AddMedicationResponse addMedication(AddMedicationRequest addMedicationRequest);
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber);
	UpdateEntryResponse editEntry(UpdateEntryRequest updateEntryRequest);

	void removePharmacistByEmailOrPractitionerIdentificationNumber(RemovePractitionerRequest removePractitionerRequest);
   }
