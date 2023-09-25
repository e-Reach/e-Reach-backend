package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.ApiResponse;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.services.users.EReachPatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/patient")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class PatientController {
    private final EReachPatientService patientService;
    
    @PostMapping("create-patient/")
    public ResponseEntity<?> createPatient(@RequestBody CreatePatientRequest createPatientRequest){
        CreatePatientResponse response = null;
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


}
