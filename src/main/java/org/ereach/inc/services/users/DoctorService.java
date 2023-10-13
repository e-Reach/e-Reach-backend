package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;

import java.time.LocalDate;
import java.util.List;

public interface DoctorService {
	
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber);
	MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date);
}
