package org.ereach.inc.services.users;

import lombok.SneakyThrows;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.response.CreateHospitalResponse;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest
class HospitalAdminServiceTest {
	
	@Autowired
	private HospitalAdminService hospitalAdminService;
	private CreateHospitalResponse response;
	@BeforeEach
	@SneakyThrows
	void startEachTest() {
		response = hospitalAdminService.registerHospital(buildCreateHospitalRequest());
	}
	
	private CreateHospitalRequest buildCreateHospitalRequest() {
		return CreateHospitalRequest.builder()
				       .hospitalName("Glory Be To God Hospital")
				       .adminEmail("glory@yahoo.com")
				       .hospitalEmail("alaabdulmalik03@gmail.com")
				       .adminFirstName("Glory")
				       .adminLastName("Oyedotun")
				       .HEFAMAA_ID("HefamaaId")
				       .hospitalPhoneNumber("07036174617")
				       .adminPhoneNumber("08023677114")
				       .state("Lagos")
				       .streetNumber("342B")
				       .streetName("herbert Macaulay way")
				       .build();
	}
	
	@AfterEach
	void tearDown() {
	
	}
	
	@Test void testThatHospitalAccountsCanBeCreated(){
		assertThat(response).isNotNull();
		
	}
	
	@Test void testThatHospitalRegistrationRequestHasIncompleteDetails_NullPointerExceptionIsThrown(){
		assertThatThrownBy(()-> hospitalAdminService.registerHospital(CreateHospitalRequest.builder()
				                                    .hospitalPhoneNumber("+234-703-617-461-7")
				                                    .hospitalEmail("alaabdulmalik03@gmail.com")
				                                    .streetNumber("345")
				                                    .build()))
								.isExactlyInstanceOf(NullPointerException.class)
								.hasMessageContaining("null");
	}
	
	@Test void hospitalTriesToRegisterWithInvalidEmailOrPhoneNumber_ExceptionIsThrownTest(){
		assertThatThrownBy(()-> hospitalAdminService.registerHospital(buildRequestWithInvalidEmailAndPassword()))
													.isInstanceOf(RegistrationFailedException.class)
													.hasMessage("Registration Failed: Invalid email");
	}
	
	@Test void testThatHospitalAccountIsNotCreated_IfHospitalsAreNotVerifiedByHEFAMAA(){
		assertThatThrownBy(()->{
			
						})
				.isInstanceOf(RegistrationFailedException.class);
	}
	
	@Test void testThatHospitalAdminAccountIsNotCreatedIf_HospitalAccountDoesNotExistsYet(){
	
	}
	
	@Test void testThatHospitalAdminAccountIsCreated_AfterHospitalAccountIsCreated(){
	
	}
	
	@Test void testThatHospitalAdminUsernameIsGenerated_AndSentToAdminEmail_AfterAdminAccountIsSetUp(){
	
	}
	
	@Test void testThatAdminTriesToRegisterWithInvalidEmailAndPhoneNumber_ExceptionIsThrown(){
	
	}
	
	@Test void testThatHospitalUniqueUrlIsGeneratedAndSentToTheHospitalEmailAddress_IfBothRegistrationAReSuccessful(){
	
	}
	
	private CreateHospitalRequest buildRequestWithInvalidEmailAndPassword() {
		return CreateHospitalRequest.builder()
				       .hospitalName("Glory Be To God Hospital")
				       .adminEmail("gmail.com")
				       .hospitalEmail(".com")
				       .adminFirstName("Glory")
				       .adminLastName("Oyedotun")
				       .HEFAMAA_ID("HefamaaId")
				       .hospitalPhoneNumber("174617")
				       .adminPhoneNumber("08023677114")
				       .state("Lagos")
				       .streetNumber("342B")
				       .build();
	}
}