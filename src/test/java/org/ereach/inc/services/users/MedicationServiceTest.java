package org.ereach.inc.services.users;

import org.ereach.inc.services.hospital.MedicationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

    public CreateMedicationRequest buildMedication(){
        return  CreateMedicationRequest.builder.build
    }
}
