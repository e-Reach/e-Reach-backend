package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;

import java.time.LocalDate;

public interface PharmacistService {
	AddMedicationResponse addMedication(AddMedicationRequest addMedicationRequest);
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber);
	MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date);
	UpdateEntryResponse editEntry(UpdateEntryRequest updateEntryRequest);
	void removePharmacistByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber);
   }
