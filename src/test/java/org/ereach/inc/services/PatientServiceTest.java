package org.ereach.inc.services;

import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testThatNoRequiredFieldIsEmpty_ElseExceptionIsThrown() {
        CreatePatientRequest createPatientRequest = buildRequiredFields();
        assertThrows(
                RequestInvalidException.class,
                () -> {
                    patientService.createPatient(createPatientRequest);
                },
                "Incomplete entry, all fields must be completed"
        );
    }

    public CreatePatientRequest buildRequiredFields() {
        return CreatePatientRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .nin("2333")
                .email("JohnDoe@gmail.com")
                .phoneNumber("08082345677")
                .build();
    }

    @Test
    void testThatPatientIdentificationNumberIsSentToPatientImmediatelyAfterRegistration() {
        CreatePatientRequest createPatientRequest = buildPatientFieldsForPatientIdentificationNumber();

        assertThrows(
                RegistrationFailedException.class,
                () -> {
                    CreatePatientResponse createPatientResponse = patientService.createPatient(createPatientRequest);

                    if (!createPatientResponse.getMessage().contains("Patient created successfully") ||
                            !createPatientResponse.getMessage().contains(EReachPatientService.getTestPIN())) {
                        throw new RegistrationFailedException("Registration Not successful, patient is yet to receive an Identification Number");
                    }
                },
                "Registration Not successful, patient is yet to receive an Identification Number"
        );
    }

    public CreatePatientRequest buildPatientFieldsForPatientIdentificationNumber() {
        return CreatePatientRequest.builder()
                .firstName("Rich")
                .email("rich@gmail.com")
                .build();

    }


    @Test
    void testThatPatientReceivesEReachUsernameOnSuccessfulRegistration() {
        CreatePatientRequest patientRequest = buildPatientFieldsForEreachUsername();
        assertThrows(
                RegistrationFailedException.class,
                () -> {
                    CreatePatientResponse response = patientService.createPatient(patientRequest);

                    if (!response.getMessage().contains("Patient created successfully") ||
                            !response.getMessage().contains(EReachPatientService.getTestUsername())) {
                        throw new RegistrationFailedException("Failed to receive a valid eReach username");
                    }
                },
                "Patient should receive a valid eReach username on successful registration"
        );
    }

    public CreatePatientRequest buildPatientFieldsForEreachUsername(){
        return CreatePatientRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@gmail.com")
                .build();
    }

    @Test
    void testThatExceptionIsThrown_IfInvalidDataIsEntered() {
        CreatePatientRequest patientRequest = buildInvalidPatientData();

        assertThrows(
                FieldInvalidException.class,
                () -> {
                    patientService.createPatient(patientRequest);
                },
                "Invalid fields, all fields must be in the correct format"
        );
    }

    public CreatePatientRequest buildInvalidPatientData() {
        return CreatePatientRequest.builder()
                .email("j Doe@ gmail.com")
                .lastName("13john")
                .firstName("k8Doe")
                .nin("ie1234")
                .phoneNumber("sD080234576")
                .build();
    }


    @Test void updatePatientDetailsTest(){

    }

    @Test void testThatPatientHasToProvideAValidPINBeforeTheirDetailsCanBeUpdated_ElseExceptionIsThrown(){

    }

    @Test void viewRecordTest(){

    }
    @Test void viewMedicalLogsTest(){

    }

    @Test void testThatPatientHasToProvideAValidPINBeforeTheyCanViewTheirRecords_ElseExceptionIsThrown(){

    }
}
