package org.ereach.inc.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.response.ApiResponse;
import org.ereach.inc.data.dtos.response.entries.GetMedicationResponse;
import org.ereach.inc.data.models.hospital.Medication;
import org.ereach.inc.services.hospital.EreachMedicationService;
import org.ereach.inc.services.hospital.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/medication/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j

public class MedicationController {
    private EreachMedicationService medicationService;
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
