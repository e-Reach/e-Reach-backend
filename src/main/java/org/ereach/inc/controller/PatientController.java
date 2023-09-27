package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.ApiResponse;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.services.users.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/patient")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class PatientController {
    private final PatientService patientService;
    
    @PostMapping("create-patient/")
    public ResponseEntity<?> createPatient(@RequestBody CreatePatientRequest createPatientRequest){
        CreatePatientResponse response;
        ApiResponse<CreatePatientResponse> apiResponse = new ApiResponse<>();
        try {
            response = patientService.createPatient(createPatientRequest);
            apiResponse.setData(response);
            apiResponse.setSuccessful(HttpStatus.CREATED.is2xxSuccessful());
            apiResponse.setStatusCode(HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.valueOf(apiResponse.getStatusCode()));
        } catch (EReachBaseException baseException) {
            log.error("error occurred", baseException);
            response = new CreatePatientResponse();
            response.setMessage(baseException.getMessage());
            apiResponse.setData(response);
            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            apiResponse.setSuccessful(HttpStatus.BAD_REQUEST.is4xxClientError());
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("view-records/{patientIdentificationNumber}")
    public ResponseEntity<?> viewRecord(@PathVariable String patientIdentificationNumber){
        ApiResponse<GetRecordResponse> apiResponse = new ApiResponse<>();
        GetRecordResponse response;
        try {
            response = patientService.viewRecord(patientIdentificationNumber);
            apiResponse.setData(response);
            apiResponse.setStatusCode(HttpStatus.FOUND.value());
            apiResponse.setSuccessful(HttpStatus.FOUND.is2xxSuccessful());
            return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
        }catch(EReachBaseException baseException){
            response = new GetRecordResponse();
            response.setMessage(baseException.getMessage());
            apiResponse.setData(response);
            apiResponse.setSuccessful(HttpStatus.NOT_FOUND.is2xxSuccessful());
            apiResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("upload-profile-picture")
    public ResponseEntity<?> uploadProfilePicture(@RequestBody MultipartFile multipartFile){
        try {
            return patientService.uploadProfile(multipartFile);
        } catch (EReachBaseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    }
}
