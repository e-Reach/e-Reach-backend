package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RequestInvalidException;

import java.util.List;

public interface HospitalAdminService {
	
	HospitalResponse registerHospital(CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException;
	HospitalResponse saveHospitalAdminPermanently(String token) throws RequestInvalidException;
	PractitionerResponse invitePractitioner(CreatePractitionerRequest practitionerRequest);
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
