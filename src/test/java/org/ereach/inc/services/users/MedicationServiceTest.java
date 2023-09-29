package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.services.hospital.MedicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest

public class MedicationServiceTest {
    private static final String MEDICATION_ADDED_SUCCESSFULY = "Added Successfully";
    @Autowired
    private MedicationService medicationService;
    private AddMedicationResponse medicationResponse;
    @Test
    public void testThatMedicationCanAdded() {
        AddMedicationRequest addMedicationRequest = buildMedication();
        AddMedicationResponse addMedicationResponse = medicationService.createMedication(addMedicationRequest);
//        assertThat(a).isNotNull();
//        assertThat(addMedicationResponse.getMessage()).isEqualTo(MEDICATION_ADDED_SUCCESSFULY);
        
    }
    public void testThatMedicationCanBeCreated(){
        AddMedicationRequest medicationRequest = medicationRequest();
        
    }

    @Test
    

    public void testThatMedicationsCanBeViewed(){


    }

    @Test


    public void testThatMedicationCanBeDeleted(){

    }

    @Test


    public void testThatMedicationCanBeUpdated(){

    }
    
    public AddMedicationRequest buildMedication(){
        return AddMedicationRequest.builder()
                .price(BigDecimal.valueOf(50))
                .drugName("paracetamol")
                .timeAdded(LocalTime.now())
                .dateAdded(LocalDate.now())
                        .build();
}
    @Test
    public AddMedicationRequest medicationRequest(){
        return AddMedicationRequest.builder()
                .dateAdded(LocalDate.now())
                .drugName("paracetamol")
                .price(BigDecimal.valueOf(500))
                .timeAdded(LocalTime.now())
                .build();
    }
}
