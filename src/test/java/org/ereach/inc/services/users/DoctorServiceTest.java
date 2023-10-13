package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

@SpringBootTest
public class DoctorServiceTest {
	
	private DoctorService doctorService;
	private PatientService patientService;
	private PractitionerService practitionerService;
	
	@BeforeEach
	void startEachTestWith(){
//		practitionerService.activatePractitioner();
	}
	
	@Test
	void viewMedicalRecordTest(){
		GetRecordResponse foundRecord = doctorService.viewPatientRecord("58a8c166fa");
		
	}
	
	@Test void testThatDoctorViewsMedicalRecord_AListOfMedicalLogsIsReturned_WIthEachLogContainingAllEntries(){
	
	}
}
