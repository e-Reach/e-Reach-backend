package org.ereach.inc.services;

import lombok.SneakyThrows;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.services.users.EReachPatientService;
import org.ereach.inc.services.users.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest

public class PatientServiceTest {
    @Autowired
    private PatientService patientService;

    @Test
    @SneakyThrows
    void testThatPatientCanBeRegistered(){
        CreatePatientRequest createPatientRequest = CreatePatientRequest.builder()
                .firstName("Rich")
                .lastName("Doe")
                .phoneNumber("08033456789")
                .email("rich@gmail.com")
                .build();
        CreatePatientResponse createPatientResponse = patientService.createPatient(createPatientRequest);
        assertThat(createPatientResponse.getMessage()).isEqualTo("Patient created successfully");

    }

    @Test
    void testThatNoRequiredFieldIsEmpty_ElseExceptionIsThrown() {
        assertThatThrownBy(() -> patientService.createPatient(buildPatientWithEmptyRequiredFields()))
                                               .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    @SneakyThrows
    void testThatPatientIdentificationNumberIsSentToPatientImmediatelyAfterRegistration() {
        CreatePatientRequest createPatientRequest = buildPatient();
        CreatePatientResponse createPatientResponse = patientService.createPatient(createPatientRequest);
        assertThat(createPatientResponse).isNotNull();
    }

    public CreatePatientRequest buildPatient() {
        return CreatePatientRequest.builder()
                .firstName("Rich")
                .email("ereachinc@gmail.com")
                .lastName("Doe")
                .phoneNumber("08033456789")
                .state("Lagos")
                .country("Nigeria")
                .houseNumber("3435")
                .nin("111111259090")
                .streetName("harvey road")
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

    public CreatePatientRequest buildPatientWithEmptyRequiredFields() {
        return CreatePatientRequest.builder()
                .nin("2333")
                .phoneNumber("08082345677")
                .build();
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
}
