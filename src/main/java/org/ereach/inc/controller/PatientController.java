package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.services.users.EReachPatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/patient")
@AllArgsConstructor

public class PatientController {
    private final EReachPatientService patientService;
    ResponseEntity<CreatePatientResponse> createPatient(@RequestBody CreatePatientRequest createPatientRequest) throws EReachBaseException {
        CreatePatientResponse response = patientService.createPatient(createPatientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
