package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import org.ereach.inc.services.users.EreachDoctorService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/doctor")
@AllArgsConstructor
@CrossOrigin(origins = "*")

public class DoctorController {
    private final EreachDoctorService doctorService;


}
