package org.ereach.inc.services.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PharmacistServiceTest {
	
	@Autowired
	private PharmacistService pharmacistService;
	@BeforeEach
	void startEachTestWith() {

	}
	@Test
	void testThatPharmacistCanBeCreated(){

	}
	
	@Test void createNewPharmacistTest(){
	
	}
	
	@Test void createPharmacistsWithIncompleteDetails_ExceptionIsThrown(){
	
	}
	
	@Test void createPharmacistWithInvalidDetails_RegistrationFailedExceptionIsThrown(){
	
	}
	
	@DisplayName("test that pharmacist with already existing email will not be able to register")
	@Test void testThatEveryPharmacistCreateHasAUniqueEmail(){
	
	}
	
	@Test void testThatPharmacistCanEditEntry(){
	
	}
	
	@Test void testThatWhenPharmacistTriesToViewMedicalLog_PrescriptionEntryIsTheLogShown(){
	
	}
}