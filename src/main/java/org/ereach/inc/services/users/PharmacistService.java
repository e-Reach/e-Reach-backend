package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;

public interface PharmacistService {

	AddMedicationResponse addMedication(AddMedicationRequest addMedicationRequest);
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber);
	UpdateEntryResponse editEntry(UpdateEntryRequest updateEntryRequest);
	void removePharmacistByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber);
   }
