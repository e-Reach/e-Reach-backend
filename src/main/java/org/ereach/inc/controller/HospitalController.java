package org.ereach.inc.controller;

import org.ereach.inc.data.dtos.response.GetHospitalAdminResponse;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.response.ApiResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.hospital.HospitalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/hospital/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class HospitalController {

    private HospitalService hospitalService;

    @PostMapping("activate-account/{token}")
    public ResponseEntity<?> activateHospitalAccount(@PathVariable String token){
        HospitalResponse response;
        ApiResponse<HospitalResponse> apiResponse = new ApiResponse<>();
        try {
            response = hospitalService.saveHospitalPermanently(token);
            apiResponse.setData(response);
            apiResponse.setSuccessful(HttpStatus.CREATED.is2xxSuccessful());
            apiResponse.setStatusCode(HttpStatus.CREATED.value());
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (RequestInvalidException e) {
            response = new HospitalResponse();
            response.setMessage(e.getMessage());
            apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            apiResponse.setSuccessful(HttpStatus.BAD_REQUEST.is4xxClientError());
            apiResponse.setData(response);
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("practitioners/")
    public ResponseEntity<?> getAllHospitalPractitioners(String hospitalEmail){
        List<PractitionerResponse> response = hospitalService.getAllPractitioners(hospitalEmail);
        try {
            ApiResponse<List<PractitionerResponse>> apiResponse = new ApiResponse<>();
            apiResponse.setStatusCode(HttpStatus.FOUND.value());
            apiResponse.setSuccessful(HttpStatus.FOUND.is2xxSuccessful());
            apiResponse.setData(response);
            return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
        } catch(Throwable throwable){
            return null;
        }
        
    }
    
    public ResponseEntity<?> getAllHospitalAdmins(String hospitalEmail){
        List<GetHospitalAdminResponse> response = hospitalService.findAllAdminByHospitalEmail(hospitalEmail);
        try {
            ApiResponse<List<GetHospitalAdminResponse>> apiResponse = new ApiResponse<>();
            apiResponse.setStatusCode(HttpStatus.FOUND.value());
            apiResponse.setSuccessful(HttpStatus.FOUND.is2xxSuccessful());
            apiResponse.setData(response);
            return new ResponseEntity<>(apiResponse, HttpStatus.FOUND);
        } catch(Throwable throwable){
            return null;
        }
    }
    
    public ResponseEntity<?> getAllWorkers(){
        return null;
    }
    
    
    public ResponseEntity<?> viewActiveLogs(){
        return null;
    }
    
    
}
