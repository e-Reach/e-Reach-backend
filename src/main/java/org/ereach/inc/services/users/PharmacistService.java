package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;

public interface PharmacistService {

	AddMedicationResponse addMedication(AddMedicationRequest addMedicationRequest);
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber);
	UpdateEntryResponse editEntry(UpdateEntryRequest updateEntryRequest);
	void removePharmacistByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber);
   }
