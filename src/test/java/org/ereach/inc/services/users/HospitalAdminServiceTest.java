package org.ereach.inc.services.users;

import lombok.SneakyThrows;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.services.InMemoryDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.math.BigInteger.ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.ereach.inc.utilities.Constants.*;

@SpringBootTest
class HospitalAdminServiceTest {
	
	@Autowired
	private HospitalAdminService hospitalAdminService;
	@Autowired
	private InMemoryDatabase inMemoryDatabase;
	private HospitalResponse response;
	@Autowired
	private EReachConfig config;
	@BeforeEach
	@SneakyThrows
	void startEachTest() {
		response = hospitalAdminService.registerHospital(buildCreateHospitalRequest());
	}
	
	@AfterEach
	void tearDown() {
	
	}
	
	@Test void testThatHospitalAccountsCanBeCreated(){
		assertThat(response).isNotNull();
		Hospital savedHospitalResponse = inMemoryDatabase.getTemporarilySavedHospital(response.getId());
		assertThat(savedHospitalResponse).isNotNull();
		assertThat(savedHospitalResponse.getAdmins().size()).isGreaterThan(ZERO.intValue());
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
													.isInstanceOf(FieldInvalidException.class)
													.hasMessage("Registration Failed: Invalid email");
	}
	
	@Test void testThatHospitalAccountIsNotCreated_IfHospitalsAreNotVerifiedByHEFAMAA(){
		assertThatThrownBy(()->{
			
						})
				.isInstanceOf(RegistrationFailedException.class);
	}
	
	@Test
	@SneakyThrows
	void testThatHospitalsCanActivateTheirAccount(){
		HospitalResponse activationResponse = hospitalAdminService.saveHospitalPermanently(config.getTestToken());
		assertThat(activationResponse.getHospitalEmail()).isEqualTo(TEST_HOSPITAL_MAIL);
		assertThat(activationResponse.getHospitalName()).isEqualTo(TEST_HOSPITAL_NAME);
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
				       .streetName("Herbert macaulay")
				       .HEFAMAA_ID("HefamaaId")
				       .hospitalPhoneNumber("174617")
				       .adminPhoneNumber("08023677114")
				       .state("Lagos")
				       .streetNumber("342B")
				       .build();
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
}