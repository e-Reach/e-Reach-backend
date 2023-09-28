package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
<<<<<<< HEAD
=======
import org.ereach.inc.data.models.hospital.Medication;
import org.ereach.inc.services.hospital.EreachMedicationService;
>>>>>>> 01d0bd5190b23d77ed7b7e90002a94f4eb1d7905
import org.ereach.inc.services.hospital.MedicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest

public class MedicationServiceTest {
    private static final String MEDICATION_ADDED_SUCCESSFULY = "Added Successfully";
    @Autowired
    private MedicationService medicationService;
    private AddMedicationResponse medicationResponse;
    @Test
<<<<<<< HEAD
    public void testThatMedicationCanAdded(){
        AddMedicationRequest addMedicationRequest = buildMedication();
        AddMedicationResponse addMedicationResponse = medicationService.createMedication(addMedicationRequest);
        assertThat(addMedicationResponse).isNotNull();
        assertThat(addMedicationResponse.getMessage()).isEqualTo(MEDICATION_ADDED_SUCCESSFULY);

=======
    public void testThatMedicationCanBeCreated(){
        AddMedicationRequest medicationRequest = medicationRequest();
>>>>>>> 01d0bd5190b23d77ed7b7e90002a94f4eb1d7905
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

<<<<<<< HEAD
    public AddMedicationRequest buildMedication(){
        return  AddMedicationRequest.builder()
                .price(BigDecimal.valueOf(50))
                .drugName("paracetamol")
                .timeAdded(LocalTime.now())
                .dateAdded(LocalDate.now())
=======
    public AddMedicationRequest medicationRequest(){
        return AddMedicationRequest.builder()
                .dateAdded(LocalDate.now())
                .drugName("paracetamol")
                .price(BigDecimal.valueOf(500))
                .timeAdded(LocalTime.now())
>>>>>>> 01d0bd5190b23d77ed7b7e90002a94f4eb1d7905
                .build();
    }
}
