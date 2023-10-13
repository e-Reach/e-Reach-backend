package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.*;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface PractitionerService {
	
	PractitionerResponse invitePractitioner(InvitePractitionerRequest registerDoctorRequest) throws RegistrationFailedException;
	PractitionerLoginResponse login(PractitionerLoginRequest loginRequest) throws EReachBaseException;
	PractitionerResponse activatePractitioner(String token) throws RequestInvalidException, RegistrationFailedException;
	PractitionerResponse invitePractitioner(String token) throws RequestInvalidException, RegistrationFailedException;

	void removePractitionerByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber);
	CloudUploadResponse uploadTestResult(CloudUploadRequest cloudUploadRequest);
	ResponseEntity<?> uploadProfilePicture(MultipartFile multipartFile) throws EReachBaseException;
	GetRecordResponse viewPatientRecord(String patientIdentificationNumber, String role);
	List<GetRecordResponse> viewPatientsRecords(String hospitalEMail, String role);
	MedicalLogResponse viewPatientMedicalLog(String patientIdentificationNumber, LocalDate date);
	TestRecommendationResponse recommendTest(TestRecommendationRequest testRecommendationRequest);

	AppointmentScheduleResponse scheduleAppointment(AppointmentScheduleRequest appointmentScheduleRequest);

	List<GetPractitionerResponse> getAllPractitionersInHospital(String hospitalEmail);

	List<GetPractitionerResponse> getAllPractitioners() throws RequestInvalidException;

	void removeAll() throws RequestInvalidException;
}
