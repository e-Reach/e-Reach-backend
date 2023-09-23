package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.GetHospitalAdminResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RequestInvalidException;

import java.util.List;

public interface HospitalService {
	HospitalResponse registerHospital(CreateHospitalRequest hospitalRequest) throws FieldInvalidException, RequestInvalidException;
	HospitalResponse editHospitalProfile(UpdateHospitalRequest hospitalRequest);
	HospitalResponse saveHospitalPermanently(String token) throws RequestInvalidException;
	List<GetHospitalAdminResponse> findAllAdminByHefamaaId(String hospitalHefamaaId);
	List<GetHospitalAdminResponse> findAllAdminByHospitalEmail(String hospitalEmail);
	HospitalResponse viewHospitalProfileByEmailOrHefamaaId(String email, String hefamaaId);
	List<HospitalResponse> getAllHospitals();
	HospitalResponse findHospitalById(String id);
	HospitalResponse findHospitalByEmail(String email);
	HospitalResponse findHospitalByHefamaaId(String hefamaa_Id);
}
