package org.ereach.inc.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.services.users.HospitalAdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/hospital-admin/")
@AllArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class HospitalAdminController {
	private HospitalAdminService adminService;

	@PostMapping("register-hospital/")
	public ResponseEntity<?> registerHospital(){
		return null;
	}
}
