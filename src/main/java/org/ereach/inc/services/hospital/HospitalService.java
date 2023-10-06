package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.data.models.users.Practitioner;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RequestInvalidException;

import java.util.List;

public interface HospitalService {
	HospitalResponse registerHospital(CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException;
	HospitalResponse editHospitalProfile(UpdateHospitalRequest hospitalRequest);
	HospitalResponse saveHospitalPermanently(String token) throws EReachBaseException;
	HospitalResponse findHospitalByEmail(String email);
	
	HospitalResponse findHospitalByHefamaaId(String hefamaa_Id);
	List<GetHospitalAdminResponse> findAllAdminByHefamaaId(String hospitalHefamaaId);
	List<GetHospitalAdminResponse> findAllAdminByHospitalEmail(String hospitalEmail);
	List<GetPractitionerResponse> getAllPractitioners(String hospitalEmail);
	List<GetRecordResponse> getAllRecordsCreated(String hospitalEmail) throws EReachBaseException;
	HospitalResponse viewHospitalProfileByEmailOrHefamaaId(String email, String hefamaaId);
	
	List<HospitalResponse> getAllHospitals();
	HospitalResponse findHospitalById(String id);
	
	String removeHospital(String mail) throws RequestInvalidException;
	MedicalLogResponse addToLog(String hospitalEmail, MedicalLog medicalLog);
	
	void addToRecords(String hospitalEmail, Record savedRecord);
	
	List<MedicalLogResponse> viewPatientsMedicalLogs(String hospitalEmail);
	
	void addPractitioners(String hospitalEmail, Practitioner savedPractitioner);
}
