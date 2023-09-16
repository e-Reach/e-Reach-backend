package org.ereach.inc.services;

import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest

public class PatientServiceTest {
    @Autowired
    private PatientService patientService;

    @Test
    void testThatPatientCanBeRegistered(){
        CreatePatientRequest createPatientRequest = CreatePatientRequest.builder()
                .firstName("Rich")
                .email("rich@gmail.com")
                .build();
        CreatePatientResponse createPatientResponse = patientService.createPatient(createPatientRequest);
        assertThat(createPatientResponse.getMessage()).isEqualTo("Patient created successfully");

    }



}
