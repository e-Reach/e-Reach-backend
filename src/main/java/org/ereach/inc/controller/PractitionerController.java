package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.PractitionerLoginRequest;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.PractitionerLoginResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.services.users.PractitionerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/practitioner/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class PractitionerController {

    private PractitionerService practitionerService;

    @PostMapping("upload-profile/")
    public ResponseEntity<?> uploadProfilePicture(MultipartFile multipartFile){
        try {
            return practitionerService.uploadProfilePicture(multipartFile);
        } catch (EReachBaseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("login/")
    public ResponseEntity<?> login(PractitionerLoginRequest loginRequest){
        PractitionerLoginResponse response = practitionerService.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> viewPatientRecord(String patientId, String role){
        practitionerService.viewPatientRecord(patientId, role);
        return null;
    }

    @GetMapping("/view-patients-records/{hospitalEMail}/{role}")
    public ResponseEntity<?> viewPatientsRecords(@PathVariable String hospitalEMail, @PathVariable String role){
        List<GetRecordResponse> response = practitionerService.viewPatientsRecords(hospitalEMail, role);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }
}
