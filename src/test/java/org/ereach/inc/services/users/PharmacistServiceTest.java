package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.request.UpdateEntryRequest;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PharmacistServiceTest {
	
	@Autowired
	private PharmacistService pharmacistService;
	private CreatePractitionerRequest practitionerRequest1;
	private CreatePractitionerRequest practitionerRequest2;
	private CreatePractitionerRequest practitionerRequest3;

	private AddMedicationRequest medicationRequest1;
	private AddMedicationRequest medicationRequest2;
	private AddMedicationRequest medicationRequest3;

	private UpdateEntryRequest entryRequest;
	@BeforeEach
	void startEachTestWith() {
		practitionerRequest1 = new CreatePractitionerRequest();
		practitionerRequest1.setEmail("desyFavour54@gmail.com");
		practitionerRequest1.setFirstName("Favour");
		practitionerRequest1.setLastName("Chiemela");
//		practitionerRequest1.setPhoneNumber("09034687770");
		practitionerRequest1.setRole("pharmacist");

		practitionerRequest2 = new CreatePractitionerRequest();
		practitionerRequest2.setEmail("deegFav63@gmail.com");
		practitionerRequest2.setFirstName("Goodness");
		practitionerRequest2.setLastName("Obinali");
//		practitionerRequest2.setPhoneNumber("07034687770");
		practitionerRequest2.setRole("pharmacist");

		practitionerRequest3 = new CreatePractitionerRequest();
		practitionerRequest3.setEmail("dessyFav644@gmail.com");
		practitionerRequest3.setFirstName("Favwhite");
		practitionerRequest3.setLastName("nwadike");
//		practitionerRequest3.setPhoneNumber("07037887770");
		practitionerRequest3.setRole("pharmacist");

		medicationRequest1 = new AddMedicationRequest();
		medicationRequest1.setPrice(BigDecimal.valueOf(1500));
		medicationRequest1.setDrugName("Paracetamol");

		medicationRequest2 = new AddMedicationRequest();
		medicationRequest2.setPrice(BigDecimal.valueOf(2500));
		medicationRequest2.setDrugName("Procold");

		medicationRequest3 = new AddMedicationRequest();
		medicationRequest3.setPrice(BigDecimal.valueOf(3500));
		medicationRequest3.setDrugName("Ashewo drug");
	}
	@Test
	void testThatPharmacistCanBeCreated(){

	}
	@Disabled
	@Test void createNewPharmacistTest(){
		assertDoesNotThrow(()->{
			pharmacistService.createPharmacist(practitionerRequest1);
			pharmacistService.createPharmacist(practitionerRequest2);
			pharmacistService.createPharmacist(practitionerRequest3);
		});
	}
	
	@Test void createPharmacistsWithIncompleteDetails_ExceptionIsThrown(){
		Exception exception = assertThrows(FieldInvalidException.class, () -> {
			CreatePractitionerRequest incompleteDetails = new CreatePractitionerRequest();
//			incompleteDetails.setPhoneNumber("90934687");
			incompleteDetails.setEmail("good@");
			incompleteDetails.setFirstName("fav d ooo");
			incompleteDetails.setLastName("chi  emela");
			pharmacistService.createPharmacist(incompleteDetails);
		});
		assertEquals("Incomplete details provided", exception.getMessage());
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
	@Test
	void testThatPharmacistCanAddMedicine(){
		pharmacistService.addMedication(medicationRequest1);
		pharmacistService.addMedication(medicationRequest2);
		pharmacistService.addMedication(medicationRequest3);
	}
	
	@Test void removePharmacistsByEmailAndPractitionerIdentificationNumber(){
	
	}
	
	@Test void testThatPharmacistsAreRemovedAccordingToTheHospitalTheyBelong(){
	
	}
}