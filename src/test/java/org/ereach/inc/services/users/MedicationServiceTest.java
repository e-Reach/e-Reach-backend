package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.AddMedicationRequest;
import org.ereach.inc.data.dtos.response.AddMedicationResponse;
import org.ereach.inc.data.models.hospital.Medication;
import org.ereach.inc.services.hospital.EreachMedicationService;
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


@SpringBootTest

public class MedicationServiceTest {
    private MedicationService medicationService;
    private AddMedicationResponse medicationResponse;
    @Test
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

    public AddMedicationRequest medicationRequest(){
        return AddMedicationRequest.builder()
                .dateAdded(LocalDate.now())
                .drugName("paracetamol")
                .price(BigDecimal.valueOf(500))
                .timeAdded(LocalTime.now())
                .build();
    }
}
