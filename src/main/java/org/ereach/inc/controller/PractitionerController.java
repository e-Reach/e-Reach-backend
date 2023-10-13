package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.PractitionerLoginRequest;
import org.ereach.inc.data.dtos.response.PractitionerLoginResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.services.users.PractitionerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        PractitionerLoginResponse response;
        try {
            response = practitionerService.login(loginRequest);
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } catch (EReachBaseException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    
}
