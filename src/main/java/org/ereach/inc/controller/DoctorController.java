package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.CreateDoctorRequest;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreateDoctorResponse;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.services.users.EReachPatientService;
import org.ereach.inc.services.users.EreachDoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/doctor")
@AllArgsConstructor
@CrossOrigin(origins = "*")

public class DoctorController {
    private final EreachDoctorService doctorService;

    @PostMapping
    ResponseEntity<CreateDoctorResponse> registerNewDoctor(@RequestBody CreateDoctorRequest registerDoctorRequest) throws EReachBaseException {
        CreateDoctorResponse response = doctorService.registerNewDoctor(registerDoctorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
