package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.services.hospital.MedicationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest

public class MedicationServiceTest {
    private MedicationService medicationService;


    @Test

    public void testThatMedicationCanBeCreated(){

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
