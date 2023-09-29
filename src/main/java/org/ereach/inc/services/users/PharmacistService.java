package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.*;

public interface PharmacistService {

	AddMedicationResponse addMedication(AddMedicationRequest addMedicationRequest);
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber);
	EntryResponse editEntry(UpdateEntryRequest updateEntryRequest);
	void removePharmacistByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber);
   }
