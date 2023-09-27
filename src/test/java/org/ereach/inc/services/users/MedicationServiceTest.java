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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest

public class MedicationServiceTest {
    private static final String MEDICATION_ADDED_SUCCESSFULY = "Added Successfully";
    @Autowired
    private MedicationService medicationService;


    @Test
    public void testThatMedicationCanAdded(){
        AddMedicationRequest addMedicationRequest = buildMedication();
        AddMedicationResponse addMedicationResponse = medicationService.createMedication(addMedicationRequest);
        assertThat(addMedicationResponse).isNotNull();
        assertThat(addMedicationResponse.getMessage()).isEqualTo(MEDICATION_ADDED_SUCCESSFULY);

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
        return  AddMedicationRequest.builder()
                .price(BigDecimal.valueOf(50))
                .drugName("paracetamol")
                .timeAdded(LocalTime.now())
                .dateAdded(LocalDate.now())
                .build();
    }
}
