package org.ereach.inc.services;

import org.ereach.inc.data.dtos.request.*;
import org.ereach.inc.data.dtos.response.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.ereach.inc.services.EReachAddressService.getTestId;

@SpringBootTest
class AddressServiceTest {
	
	@Autowired
	private AddressService addressService;
	private AddressCreationResponse addressCreationResponse;
	
	@BeforeEach
	void startEachTestWith() {
		addressService.deleteAll();
		AddressCreationRequest addressCreationRequest = buildCreationRequest();
		addressCreationResponse = addressService.saveAddress(addressCreationRequest);
	}
	
	@Test
	void saveNewAddressTest() {
		assertThat(addressCreationResponse).isNotNull();
		assertThat(addressCreationResponse.getId()).isNotNull();
		assertThat(addressCreationResponse.getCountry()).isEqualTo("Nigeria");
	}
	
	@Test
	void updateAddressTest() {
		AddressUpdateRequest addressUpdateRequest = buildUpdateRequest();
		AddressUpdateResponse updateResponse = addressService.updateAddress(addressUpdateRequest);
		assertThat(updateResponse.getCountry()).isEqualTo(addressUpdateRequest.getCountry());
		assertThat(updateResponse.getState()).isEqualTo(updateResponse.getState());
	}
	
	@Test
	void getAllAddressesTest() {
	
	}
	
	private static AddressCreationRequest buildCreationRequest() {
		return AddressCreationRequest.builder()
				       .country("Nigeria")
				       .houseNumber("34B")
				       .state("Lagos")
				       .street("Semicolon Street")
				       .build();
	}
	
	private AddressUpdateRequest buildUpdateRequest() {
		return AddressUpdateRequest.builder()
				       .country("Ghana")
				       .id(getTestId())
				       .houseNumber("43G")
				       .build();
	}
}