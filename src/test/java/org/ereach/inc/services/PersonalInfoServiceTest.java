package org.ereach.inc.services;

import org.ereach.inc.data.dtos.request.CreatePersonalInfoRequest;
import org.ereach.inc.data.dtos.request.UpdatePersonalInfoRequest;
import org.ereach.inc.data.dtos.response.PersonalInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@SpringBootTest
class PersonalInfoServiceTest {
	
	
	@Autowired
	private PersonalInfoService personalInfoService;
	PersonalInfoResponse response;
	@BeforeEach void startEachTestWith(){
		CreatePersonalInfoRequest personalInfo = buildPersonalInfo();
		response = personalInfoService.addPersonalInfo(personalInfo);
	}
	
	@Test
	void testSavePersonalInfo() {
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
	}
	
	@Test
	void testUpdatePersonalInfo() {
		UpdatePersonalInfoRequest personalInfoRequest = buildUpdateRequest();
		PersonalInfoResponse updateResponse = personalInfoService.updatePersonalInfo(personalInfoRequest);
		assertThat(response.getKnownHealthConditions()).isNotNull();
		assertThat(response.getKnownHealthConditions()).isNotEqualTo("Asthma");
		
		assertThat(updateResponse.getAllergy()).isEqualTo("Groceries");
		assertThat(updateResponse.getPhoneNumber()).isEqualTo("08023677114");
		assertThat(response.getKnownHealthConditions()).isNotEqualTo(updateResponse.getKnownHealthConditions());
	}
	
	@Test
	void testDeletePersonalInfo() {
	
	}
	
	@Test
	void testGetPersonalInfoById() {
	
	}
	
	
	private CreatePersonalInfoRequest buildPersonalInfo() {
		return CreatePersonalInfoRequest.builder()
				       .allergy("Cancer")
				       .email("alaabdulmalik03@gmail.com")
				       .phoneNumber("07036174617")
				       .knownHealthConditions("Unknown")
				       .country("Nigeria")
				       .houseNumber("34B")
				       .state("Lagos")
				       .streetName("Semicolon Street")
				       .streetNumber("343B")
				       .build();
	}
	
	private UpdatePersonalInfoRequest buildUpdateRequest() {
		return UpdatePersonalInfoRequest.builder()
				       .id(EReachPersonalInfoService.getTestId())
				       .allergy("Groceries")
				       .knownHealthConditions("Asthma")
				       .phoneNumber("08023677114")
				       .build();
	}
}