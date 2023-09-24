package org.ereach.inc.services.users;

import lombok.SneakyThrows;
import org.ereach.inc.data.dtos.request.InvitePractitionerRequest;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.utilities.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static java.math.BigInteger.ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PractitionerServiceTest {
	
	@Autowired
	private HospitalAdminService adminService;
	@Autowired
	private PractitionerService practitionerService;
	
	@BeforeEach
	void startEachTestWith() {
	
	}
	
	@Test
	@SneakyThrows
	void testThatPractitionerCanActivateTheirAccount_AfterActivationPractitionerAccountIsNowActive(){
		ResponseEntity<?> invitationResponse = adminService.invitePractitioner(buildPractitionerInviteRequest());
		assertEquals(invitationResponse.getStatusCode().value(), HttpStatusCode.valueOf(201).value());
		
		PractitionerResponse activatedPractitioner = practitionerService.activatePractitionerAccount(JWTUtil.getTestToken());
		assertThat(activatedPractitioner.getMessage()).isNotNull();
		assertTrue(practitionerService.getAllPractitioners().size() > ZERO.intValue());
	}
	
	private InvitePractitionerRequest buildPractitionerInviteRequest() {
		return InvitePractitionerRequest.builder()
				       .role("doctor")
				       .email("chidianucha@gmail.com")
				       .firstName("Chidi")
				       .lastName("Anucha")
				       .build();
	}
}