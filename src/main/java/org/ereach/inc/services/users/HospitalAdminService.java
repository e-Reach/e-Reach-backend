package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.*;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.exceptions.RequestInvalidException;

import java.util.List;

public interface HospitalAdminService {
	
	HospitalResponse registerHospital(CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException;
	HospitalAdminResponse saveHospitalAdminPermanently(String token) throws RequestInvalidException;
	CreatePatientResponse registerPatient(CreatePatientRequest createPatientRequest) throws EReachBaseException;
	PractitionerResponse invitePractitioner(InvitePractitionerRequest practitionerRequest) throws FieldInvalidException, RequestInvalidException, RegistrationFailedException;
	HospitalResponse editHospitalProfile(UpdateHospitalRequest hospitalRequest);
	GetHospitalAdminResponse findAdminById(String id, String hospitalEmail);
	GetHospitalAdminResponse findAdminByEmail(String adminEmail, String hospitalEmail);
	PractitionerResponse removePractitioner(String email);
	CreatePatientResponse removePatient(String patientId, String hospitalHefamaaId);
	List<PractitionerResponse> viewAllPractitioners(String hospitalHefamaaId);
	List<GetRecordResponse> viewAllRecordsCreatedByHospital(String hefamaaId);
	
	void importPatientDetails();
	
	HospitalResponse getHospitalRegisteredWith(String officerIdentificationNumber);
}
