package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RequestInvalidException;

import java.util.List;

public interface HospitalAdminService {
	
	HospitalResponse registerHospital(CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException;
	PractitionerResponse invitePractitioner(CreatePractitionerRequest practitionerRequest);
	HospitalResponse editHospitalProfile(UpdateHospitalRequest hospitalRequest);
	PractitionerResponse removePractitioner(String email);
	CreatePatientResponse removePatient(String patientId, String hospitalHefamaaId);
	List<PractitionerResponse> viewAllPractitioners(String hospitalHefamaaId);
	List<GetRecordResponse> viewAllRecordsCreatedByHospital(String hefamaaId);
	void importPatientDetails();
	HospitalResponse getHospitalRegisteredWith(String officerIdentificationNumber);
	
	HospitalResponse saveHospitalPermanently(String token) throws RequestInvalidException;
}
