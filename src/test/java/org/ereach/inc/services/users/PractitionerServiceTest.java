package org.ereach.inc.services.users;

import lombok.SneakyThrows;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.InvitePractitionerRequest;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.utilities.JWTUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static java.math.BigInteger.ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.ereach.inc.utilities.Constants.ACCOUNT_ACTIVATION_SUCCESSFUL;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PractitionerServiceTest {
	
	@Autowired
	private HospitalAdminService adminService;
	@Autowired
	private PractitionerService practitionerService;
	private ResponseEntity<?> invitationResponse;
	private PractitionerResponse activatedPractitioner;
	
	@BeforeEach
	@SneakyThrows
	void startEachTestWith() {
		invitationResponse = adminService.invitePractitioner(buildPractitionerInviteRequest());
		activatedPractitioner = practitionerService.activatePractitionerAccount(JWTUtil.getTestToken());
	}
	
	@AfterEach
	@SneakyThrows
	void endAllTestWith(){
		practitionerService.removeAll();
	}
	
	
	@Test
	@SneakyThrows
	void testThatPractitionerCanActivateTheirAccount_AfterActivationPractitionerAccountIsNowActive(){
		assertEquals(invitationResponse.getStatusCode().value(), HttpStatusCode.valueOf(201).value());
		assertThat(activatedPractitioner.getMessage()).isNotNull();
		assertThat(activatedPractitioner.getMessage()).isEqualTo(ACCOUNT_ACTIVATION_SUCCESSFUL);
		assertTrue(practitionerService.getAllPractitioners().size() > ZERO.intValue());
	}
	
	@Test void createPractitionerWithIncompleteDetails_ExceptionIsThrown(){
		assertThatThrownBy(() -> practitionerService.activatePractitionerAccount(buildPractitionerWithIncomplete()))
													.isInstanceOf(NullPointerException.class)
													.hasMessageContaining("");
	}
	
	@Test
	@SneakyThrows
	void createPractitionerWithInvalidDetails_RegistrationFailedExceptionIsThrown(){
		assertThatThrownBy(()->adminService.invitePractitioner(buildInviteRequestWithInvalidDetails()))
										   .isInstanceOf(FieldInvalidException.class)
										   .hasMessageContaining("Invalid email");
	}
	
	private InvitePractitionerRequest buildInviteRequestWithInvalidDetails() {
		return InvitePractitionerRequest.builder()
				       .role("doctor")
				       .email("chidi@ anucha")
				       .firstName("Chidi")
				       .lastName("Anucha")
				       .build();
	}
	
	@DisplayName("test that pharmacist with already existing email will not be able to register")
	@Test void testThatEveryPractitionerCreatedHasAUniqueEmail(){
		assertThatThrownBy(()->practitionerService.activatePractitionerAccount(buildCompletePractitionerDetails()))
												  .isInstanceOf(RegistrationFailedException.class);
	}
	
	@Test void viewMedicalRecordTest(){
	
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
	
	private CreatePractitionerRequest buildCompletePractitionerDetails(){
		return CreatePractitionerRequest.builder()
				       .phoneNumber("90934687")
				       .firstName("Chidi")
				       .role("doctor")
				       .email("chidianucha@gmail.com")
				       .lastName("Anucha")
				       .build();
	}
	
	private CreatePractitionerRequest buildPractitionerWithIncomplete() {
		return CreatePractitionerRequest.builder()
				       .phoneNumber("90934687")
				       .firstName("fav d ooo")
				       .build();
	}
}