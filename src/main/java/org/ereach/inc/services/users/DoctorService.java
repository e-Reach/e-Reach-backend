package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;

import java.time.LocalDate;

public interface DoctorService {
	
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber);
	MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date);
}
