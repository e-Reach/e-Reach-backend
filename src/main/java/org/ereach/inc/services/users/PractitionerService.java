package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.*;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.exceptions.RequestInvalidException;

import java.time.LocalDate;
import java.util.List;

public interface PractitionerService {
	
	PractitionerResponse activatePractitionerAccount(CreatePractitionerRequest registerDoctorRequest);
	PractitionerResponse activatePractitionerAccount(String token) throws RequestInvalidException;
	void removePractitionerByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber);
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber);
	MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date);
	TestRecommendationResponse recommendTest(TestRecommendationRequest testRecommendationRequest);
	AppointmentScheduleResponse scheduleAppointment(AppointmentScheduleRequest appointmentScheduleRequest);
	CloudUploadResponse uploadTestResult(CloudUploadRequest cloudUploadRequest);
	List<GetPractitionerResponse> getAllPractitionersInHospital(String hospitalEmail);
	List<GetPractitionerResponse> getAllPractitioners() throws RequestInvalidException;
	
	void removeAll() throws RequestInvalidException;
}
