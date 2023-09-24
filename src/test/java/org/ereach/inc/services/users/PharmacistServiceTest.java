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
	private AddMedicationRequest medicationRequest1;
	private AddMedicationRequest medicationRequest2;
	private AddMedicationRequest medicationRequest3;

	private UpdateEntryRequest entryRequest;
	@BeforeEach
	void startEachTestWith() {
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
}