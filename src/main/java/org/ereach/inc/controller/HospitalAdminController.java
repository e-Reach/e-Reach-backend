package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.request.InvitePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateHospitalRequest;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.users.HospitalAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.ereach.inc.utilities.Constants.ADMIN_WITH_ID_DOES_NOT_EXIST_IN_HOSPITAL;

@RestController
@RequestMapping("api/v1/hospital-admin/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class HospitalAdminController {
	private HospitalAdminService adminService;
	@PostMapping("register-hospital")
	public ResponseEntity<?> registerHospital(@RequestBody CreateHospitalRequest createHospitalRequest){
		ApiResponse<HospitalResponse> apiResponse = new ApiResponse<>();
		HospitalResponse response;
		try {
			response = adminService.registerHospital(createHospitalRequest);
			apiResponse.setData(response);
			apiResponse.setSuccessful(HttpStatus.OK.is2xxSuccessful());
			apiResponse.setStatusCode(HttpStatus.OK.value());
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
	
	@PostMapping("activate-account/")
	public ResponseEntity<?> activateAccount(@RequestParam String token){
		ApiResponse<HospitalAdminResponse> apiResponse = new ApiResponse<>();
		HospitalAdminResponse response;
		try {
			response = adminService.saveHospitalAdminPermanently(token);
			apiResponse.setData(response);
			apiResponse.setSuccessful(HttpStatus.OK.is2xxSuccessful());
			apiResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(response, HttpStatus.valueOf(apiResponse.getStatusCode()));
		} catch (RequestInvalidException baseException) {
			response = new HospitalAdminResponse();
			log.error("error occurred", baseException);
			response.setMessage(baseException.getMessage());
			apiResponse.setData(response	);
			apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			apiResponse.setSuccessful(HttpStatus.BAD_REQUEST.is4xxClientError());
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("invite-practitioner/")
	public ResponseEntity<?> invitePractitioner(@RequestBody InvitePractitionerRequest invitePractitionerRequest){
		ResponseEntity<?> response;
		try {
			response = adminService.invitePractitioner(invitePractitionerRequest);
			return response;
		} catch (FieldInvalidException | RequestInvalidException e) {
			ApiResponse<String> apiResponse = new ApiResponse<>();
			apiResponse.setSuccessful(HttpStatus.BAD_REQUEST.is4xxClientError());
			apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			apiResponse.setData("");
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}
	@PostMapping("register-patient")
	public ResponseEntity<?> registerPatient(@RequestBody CreatePatientRequest createPatientRequest){
		CreatePatientResponse response;
		ApiResponse<CreatePatientResponse> apiResponse = new ApiResponse<>();
		try {
			response = adminService.registerPatient(createPatientRequest);
			apiResponse.setData(response);
			apiResponse.setSuccessful(HttpStatus.CREATED.is2xxSuccessful());
			apiResponse.setStatusCode(HttpStatus.CREATED.value());
			return new ResponseEntity<>(response, HttpStatus.valueOf(apiResponse.getStatusCode()));
		} catch (EReachBaseException e) {
			response = new CreatePatientResponse();
			response.setMessage("");
			apiResponse.setData(response);
			apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("admin/{adminEmail}/{hospitalEmail}")
	public ResponseEntity<?> findAdminByEmail(@PathVariable String adminEmail, @PathVariable String hospitalEmail){
		GetHospitalAdminResponse response;
		ApiResponse<GetHospitalAdminResponse> apiResponse = new ApiResponse<>();
		try{
			response = adminService.findAdminByEmail(adminEmail, hospitalEmail);
			apiResponse.setData(response);
			apiResponse.setStatusCode(HttpStatus.FOUND.value());
			apiResponse.setSuccessful(HttpStatus.FOUND.is2xxSuccessful());
			return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
		}catch (Throwable throwable){
			ApiResponse<String> failureResponse = new ApiResponse<>();
			failureResponse.setData(ADMIN_WITH_ID_DOES_NOT_EXIST_IN_HOSPITAL);
			failureResponse.setSuccessful(HttpStatus.NOT_FOUND.is4xxClientError());
			failureResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(failureResponse, HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("edit-profile/")
	public ResponseEntity<?> editHospitalProfile(@RequestBody UpdateHospitalRequest hospitalRequest){
		HospitalResponse response;
		ApiResponse<HospitalResponse> apiResponse = new ApiResponse<>();
		try {
			response = adminService.editHospitalProfile(hospitalRequest);
			apiResponse.setData(response);
			apiResponse.setStatusCode(HttpStatus.ACCEPTED.value());
			apiResponse.setSuccessful(HttpStatus.ACCEPTED.is2xxSuccessful());
			return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
		}catch (Throwable throwable){
			response = new HospitalResponse();
			response.setMessage("update failed");
			apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			apiResponse.setData(response);
			apiResponse.setSuccessful(HttpStatus.BAD_GATEWAY.is4xxClientError());
			return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
		}
	}
}
