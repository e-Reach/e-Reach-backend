package org.ereach.inc.services.users;

import lombok.SneakyThrows;
import org.ereach.inc.config.EReachConfig;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.InvitePractitionerRequest;
import org.ereach.inc.data.dtos.response.GetHospitalAdminResponse;
import org.ereach.inc.data.dtos.response.HospitalAdminResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.services.InMemoryDatabase;
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.utilities.JWTUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static java.math.BigInteger.ZERO;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.ereach.inc.utilities.Constants.*;

@SpringBootTest
class HospitalAdminServiceTest {
	
	@Autowired
	private HospitalAdminService hospitalAdminService;
	@Autowired
	private HospitalService hospitalService;
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
	@SneakyThrows
	void tearDown() {
//		hospitalService.removeHospital("alaabdulmalik03@gmail.com");
		inMemoryDatabase.deleteAll("admin");
		hospitalAdminService.deleteByEmail("alaabdulmalik03@gmail.com");
		inMemoryDatabase.deleteAll("hospital");
	}
	
	@Test void testThatHospitalsAreSavedIn_InMemoryDatabaseAndCanBeRetrievedWithTheirAdmin(){
		assertThat(response).isNotNull();
		Hospital savedHospitalResponse = inMemoryDatabase.retrieveHospitalFromInMemory(response.getHospitalEmail());
		assertThat(savedHospitalResponse).isNotNull();
		assertThat(savedHospitalResponse.getHospitalEmail()).isNotNull();
		assertThat(savedHospitalResponse.getAdmins().stream().findFirst().get().getAdminEmail()).isNotNull();
		assertThat(savedHospitalResponse.getAdmins().size()).isGreaterThan(ZERO.intValue());
	}
	
	@Test void testThatHospitalRegistrationRequestHasIncompleteDetails_NullPointerExceptionIsThrown(){
		assertThatThrownBy(()-> hospitalAdminService.registerHospital(CreateHospitalRequest.builder()
				                                    .hospitalPhoneNumber("+234-703-617-461-7")
				                                    .hospitalEmail("alaabdulmalik03@gmail.com")
				                                    .postalCode("345")
				                                    .build()))
								.isExactlyInstanceOf(NullPointerException.class)
								.hasMessageContaining("null");
	}
	
	@Test void hospitalTriesToRegisterWithInvalidEmailOrPhoneNumber_ExceptionIsThrownTest(){
		assertThatThrownBy(()-> hospitalAdminService.registerHospital(buildRequestWithInvalidEmailAndPassword()))
													.isInstanceOf(FieldInvalidException.class)
													.hasMessage(String.format(CONSTRAINT_VIOLATION_MESSAGE,
															"[gmail.com, outlook.com, yahoo.com, hotmail.com, semicolon.africa.com, " +
																	"hotmail.co.uk, freenet.de]"));
	}
	
	@Test void testThatHospitalAccountIsNotCreated_IfHospitalsAreNotVerifiedByHEFAMAA(){
		
	}
	
	@Test
	@SneakyThrows
	void testThatHospitalsCanActivateTheirAccount(){
		HospitalResponse activationResponse = hospitalService.saveHospitalPermanently(config.getTestToken());
		assertThat(activationResponse.getHospitalEmail()).isEqualTo(TEST_HOSPITAL_MAIL);
		assertThat(activationResponse.getHospitalName()).isEqualTo(TEST_HOSPITAL_NAME);
	}
	
	@SneakyThrows
	@Test void testThatHospitalAdminCanActivateTheir_Account(){
		HospitalAdminResponse activationResponse = hospitalAdminService.saveHospitalAdminPermanently(config.getTestToken());
		assertThat(activationResponse.getAdminEmail()).isEqualTo(TEST_HOSPITAL_MAIL);
		assertThat(activationResponse.getAdminFirstName()).isEqualTo(TEST_HOSPITAL_NAME);
		assertThat(activationResponse.getAdminLastName()).isEqualTo(TEST_HOSPITAL_NAME);
	}
	
	@Test void testThatHospitalAdminAccountIsNotCreatedIf_HospitalAccountDoesNotExistsYet() {
//		hospitalAdminService.f
	}
	
	@SneakyThrows
	@Test
	void testThatHospitalAdminAccountIsCreated_AfterHospitalAccountIsCreated(){
		HospitalResponse savedHospitalResponse = hospitalService.saveHospitalPermanently(JWTUtil.getTestToken());
		assertThat(savedHospitalResponse).isNotNull();
		HospitalResponse foundHospital = hospitalService..
		findHospitalByEmail(savedHospitalResponse.getHospitalEmail());
		assertThat(foundHospital).isNotNull();
		
		HospitalAdminResponse savedAdminResponse = hospitalAdminService.saveHospitalAdminPermanently(JWTUtil.getTestToken());
		assertThat(savedAdminResponse).isNotNull();
		GetHospitalAdminResponse foundAdmin = hospitalAdminService.findAdminByEmail(savedAdminResponse.getAdminEmail(), foundHospital.getHospitalEmail());
		assertThat(foundAdmin).isNotNull();
	}
	
	@Test
	@SneakyThrows
	void invitePractitionerTest(){
		PractitionerResponse invitationResponse = hospitalAdminService.invitePractitioner(buildPractitionerInvitationRequest());
		
	}
	
	@Test
	void medicalLogsCanBeFetched_UsingTheEmailOfTheHospitalCreatedAt() {
		List<MedicalLogResponse> foundLogs = hospitalService.viewPatientsMedicalLogs(response.getHospitalEmail());
//		assertThat(foundLogs.isEmpty()).isFalse();
//		foundLogs.forEach(logResponse->{
//			assertThat(logResponse).isNotNull();
//			assertThat(logResponse.getMessage()).isNotNull();
//			assertThat(logResponse.getPatientIdentificationNumber()).isNotNull();
//		});
	}
	
	
	private InvitePractitionerRequest buildPractitionerInvitationRequest() {
		return InvitePractitionerRequest.builder()
				       .email("alaabdulmalik03@gmail.com")
				       .role("doctor")
				       .firstName("Eniola")
				       .lastName("Samuel")
				       .phoneNumber("09045678798")
				       .hospitalEmail("alaabdulmalik03@gmail.com")
				       .build();
	}
	
	private CreateHospitalRequest buildRequestWithInvalidEmailAndPassword() {
		return CreateHospitalRequest.builder()
				       .hospitalName("Glory Be To God Hospital")
				       .adminEmail("gmail.com")
				       .hospitalEmail(".com")
				       .adminFirstName("Glory")
				       .adminLastName("Oyedotun")
				       .streetAddress("Herbert macaulay")
				       .HEFAMAA_ID("Obodo")
				       .hospitalPhoneNumber("174617")
				       .adminPhoneNumber("08023677114")
				       .state("Lagos")
				       .postalCode("342B")
				       .build();
	}
	
	private CreateHospitalRequest buildCreateHospitalRequest() {
		return CreateHospitalRequest.builder()
				       .hospitalName("Glory Be To God Hospital")
				       .adminEmail("glory@yahoo.com")
				       .hospitalEmail("alaabdulmalik03@gmail.com")
				       .adminFirstName("Glory")
				       .adminLastName("Oyedotun")
				       .HEFAMAA_ID("I12345679008")
				       .hospitalPhoneNumber("07036174617")
				       .adminPhoneNumber("08023677114")
				       .state("Lagos")
				       .postalCode("342B")
				       .streetAddress("herbert Macaulay way")
				       .build();
	}
}