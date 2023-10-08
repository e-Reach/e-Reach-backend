package org.ereach.inc.services.users;

import lombok.SneakyThrows;
import org.ereach.inc.data.dtos.request.InvitePractitionerRequest;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.utilities.JWTUtil;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	private PractitionerResponse invitationResponse;
	private PractitionerResponse activatedPractitioner;
	
	@BeforeEach
	@SneakyThrows
	void startEachTestWith() {
		invitationResponse = adminService.invitePractitioner(buildPractitionerInviteRequest());
		activatedPractitioner = practitionerService.activatePractitioner(JWTUtil.getTestToken());
	}
	
	@AfterEach
	@SneakyThrows
	void endAllTestWith(){
		practitionerService.removeAll();
	}
	
	
	@Test
	@SneakyThrows
	void testThatPractitionerCanActivateTheirAccount_AfterActivationPractitionerAccountIsNowActive(){
		assertThat(activatedPractitioner.getMessage()).isNotNull();
		assertThat(activatedPractitioner.getMessage()).isEqualTo(ACCOUNT_ACTIVATION_SUCCESSFUL);
		assertTrue(practitionerService.getAllPractitioners().size() > ZERO.intValue());
	}
	
	@Test void createPractitionerWithIncompleteDetails_ExceptionIsThrown(){
		assertThatThrownBy(() -> practitionerService.invitePractitioner(buildPractitionerWithIncomplete()))
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
		InvitePractitionerRequest request = new InvitePractitionerRequest();
		request.setEmail("chidi@anucha");
		request.setRole("doctor");
		request.setFirstName("Chidi");
		request.setLastName("Anucha");
		return request;
	}
	
	@DisplayName("test that pharmacist with already existing email will not be able to register")
	@Test void testThatEveryPractitionerCreatedHasAUniqueEmail(){
		assertThatThrownBy(()->practitionerService.invitePractitioner(buildCompletePractitionerDetails()))
												  .isInstanceOf(RegistrationFailedException.class);
	}
	// TODO: 10/8/2023 Coming Back To Pass The Test
	@Disabled
	@Test void viewMedicalRecordTest(){
		GetRecordResponse response = practitionerService.viewPatientRecord(invitationResponse.getPractitionerIdentificationNumber(), "doctor");
		assertThat(response).isNotNull();
		assertThat(response.getMessage()).isNotNull();
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
				       .hospitalEmail("alaabdulmalik03@gmail.com")
				       .phoneNumber("08181587649")
				       .build();
	}
	
	private InvitePractitionerRequest buildCompletePractitionerDetails(){
		return InvitePractitionerRequest.builder()
				       .phoneNumber("90934687")
				       .firstName("Chidi")
				       .role("doctor")
				       .email("chidianucha@gmail.com")
				       .lastName("Anucha")
				       .hospitalEmail("alaabdulmalik03@gmail.com")
				       .build();
	}
	
	private InvitePractitionerRequest buildPractitionerWithIncomplete() {
		return InvitePractitionerRequest.builder()
				       .phoneNumber("90934687")
				       .firstName("fav d ooo")
				       .build();
	}
}