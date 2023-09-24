package org.ereach.inc.services.users;

import lombok.SneakyThrows;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.InvitePractitionerRequest;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.utilities.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static java.math.BigInteger.ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.ereach.inc.utilities.Constants.ACCOUNT_ACTIVATION_SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PractitionerServiceTest {
	
	@Autowired
	private HospitalAdminService adminService;
	@Autowired
	private PractitionerService practitionerService;
	
	@BeforeEach
	@SneakyThrows
	void startEachTestWith() {
		practitionerService.removeAll();
	}
	
	
	@Test
	@SneakyThrows
	void testThatPractitionerCanActivateTheirAccount_AfterActivationPractitionerAccountIsNowActive(){
		ResponseEntity<?> invitationResponse = adminService.invitePractitioner(buildPractitionerInviteRequest());
		assertEquals(invitationResponse.getStatusCode().value(), HttpStatusCode.valueOf(201).value());
		
		PractitionerResponse activatedPractitioner = practitionerService.activatePractitionerAccount(JWTUtil.getTestToken());
		assertThat(activatedPractitioner.getMessage()).isNotNull();
		assertThat(activatedPractitioner.getMessage()).isEqualTo(ACCOUNT_ACTIVATION_SUCCESSFUL);
		assertTrue(practitionerService.getAllPractitioners().size() > ZERO.intValue());
	}
	
	@Test void createPractitionerWithIncompleteDetails_ExceptionIsThrown(){
		Exception exception = assertThrows(FieldInvalidException.class, () -> {
			CreatePractitionerRequest incompleteDetails = new CreatePractitionerRequest();
			incompleteDetails.setPhoneNumber("90934687");
			incompleteDetails.setEmail("good@");
			incompleteDetails.setFirstName("fav d ooo");
			incompleteDetails.setLastName("chiemela");
			practitionerService.activatePractitionerAccount(incompleteDetails);
		});
		assertEquals("Incomplete details provided", exception.getMessage());
	}
	@Test void createPractitionerWithInvalidDetails_RegistrationFailedExceptionIsThrown(){
	
	}
	
	@DisplayName("test that pharmacist with already existing email will not be able to register")
	@Test void testThatEveryPractitionerCreateHasAUniqueEmail(){
	
	}
	
	@Test void removePractitionerByEmailAndPractitionerIdentificationNumber(){
	
	}
	
	@Test void testThatPractitionersAreRemovedAccordingToTheHospitalTheyBelong(){
	
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