package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.response.ApiResponse;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.services.users.HospitalAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hospital-admin/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class HospitalAdminController {
	private HospitalAdminService adminService;

	@PostMapping("register-hospital/")
	public ResponseEntity<?> registerHospital(@RequestBody CreateHospitalRequest createHospitalRequest){
		ApiResponse<HospitalResponse> apiResponse = new ApiResponse<>();
		HospitalResponse response = null;
		try {
			response = adminService.registerHospital(createHospitalRequest);
			apiResponse.setData(response);
			apiResponse.setSuccessful(HttpStatus.CREATED.is2xxSuccessful());
			apiResponse.setStatusCode(HttpStatus.CREATED.value());
			return new ResponseEntity<>(response, HttpStatus.valueOf(apiResponse.getStatusCode()));
		} catch (EReachBaseException baseException) {
			response = new HospitalResponse();
			log.error("error occurred", baseException);
			response.setMessage(baseException.getMessage());
			apiResponse.setData(response);
			apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			apiResponse.setSuccessful(HttpStatus.BAD_REQUEST.is4xxClientError());
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}
}
