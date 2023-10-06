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
	String jwt = "https://localhost/3000/activate-account?token=eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJjbGFpbXMiOnsibGFzdCBuYW1lIjoiQW51Y2hhIiwiZmlyc3QgbmFtZSI6IkNoaWRpIiwidXNlciBwYXNzd29yZCI6ImRvY3RvciIsInVzZXIgbWFpbCI6ImNoaWRpYW51Y2hhQGdtYWlsLmNvbSJ9LCJleHAiOjE2OTU1NTg5MDAsImlzcyI6ImUtUmVhY2ggSW5jb3Jwb3JhdGlvbiIsImlhdCI6MTY5NTU1NTMwMH0.8uRlm77N2D-dl20-qxG71--WA70SerYEPEEbncFgot0t70_m_IpBxpDAnFXn-Vv06ozLk6Xx67BOp3Ni3EzzVg";
	
	@BeforeEach
	void startEachTestWith(){
//		practitionerService.invitePractitioner();
	}
	
	@Test
	void viewMedicalRecordTest(){
		GetRecordResponse foundRecord = doctorService.viewPatientRecord("58a8c166fa");
	}
	
	@Test void testThatDoctorViewsMedicalRecord_AListOfMedicalLogsIsReturned_WIthEachLogContainingAllEntries(){
	
	}
}
