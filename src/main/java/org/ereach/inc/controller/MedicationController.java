package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.data.dtos.response.ApiResponse;
import org.ereach.inc.data.models.hospital.Medication;
import org.ereach.inc.services.hospital.EreachMedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/medication/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j

public class MedicationController {
    private EreachMedicationService medicationService;

    @PostMapping("addMedication")
    public ResponseEntity<?> addMedication(@RequestBody AddMedicationRequest addMedicationRequest){
        AddMedicationResponse response;
        ApiResponse<AddMedicationResponse> apiResponse = new ApiResponse<>();
        try {
        response = medicationService.createMedication(addMedicationRequest);
        apiResponse.setData(response);
        apiResponse.setSuccessful(HttpStatus.CREATED.is2xxSuccessful());
        apiResponse.setStatusCode(HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.valueOf(apiResponse.getStatusCode()));
    } catch (Throwable baseException) {
        log.error("error occurred", baseException);
        response = new AddMedicationResponse();
        response.setMessage(baseException.getMessage());
        apiResponse.setData(response);
        apiResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        apiResponse.setSuccessful(HttpStatus.BAD_REQUEST.is4xxClientError());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    }


    @GetMapping("getAllMedication")
    public ResponseEntity<List<Medication>> getAllMedications() {
        List<Medication> medications = medicationService.getAllMedications();
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }

    @GetMapping("findMedicationByDrugName")
    public ResponseEntity<List<Medication>> findMedicationByDrugName(@RequestParam String drugName) {
        List<Medication> medications = medicationService.getMedicationByName(drugName);
        if (medications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(medications, HttpStatus.OK);
    }


}
