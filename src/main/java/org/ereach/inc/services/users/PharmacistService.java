package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;

import org.ereach.inc.data.dtos.request.RemovePractitionerRequest;

import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.*;

public interface PharmacistService {

	AddMedicationResponse addMedication(AddMedicationRequest addMedicationRequest);
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber);
	UpdateEntryResponse editEntry(UpdateEntryRequest updateEntryRequest);

	void removePharmacistByEmailOrPractitionerIdentificationNumber(RemovePractitionerRequest removePractitionerRequest);
	void removePharmacistByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber);
   }
