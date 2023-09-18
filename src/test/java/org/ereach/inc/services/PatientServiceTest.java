package org.ereach.inc.services;

import lombok.SneakyThrows;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.exceptions.EReachBaseException;
import org.ereach.inc.exceptions.FieldInvalidException;
import org.ereach.inc.exceptions.RegistrationFailedException;
import org.ereach.inc.services.users.EReachPatientService;
import org.ereach.inc.services.users.PatientService;
import org.junit.jupiter.api.BeforeEach;
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
    CreatePatientResponse createPatientResponse;
    
    @SneakyThrows
    @BeforeEach void beforeEachTest(){
        CreatePatientRequest createPatientRequest = buildPatient();
        createPatientResponse = patientService.createPatient(createPatientRequest);
    }
    
    @Test
    @SneakyThrows
    void testThatPatientCanBeRegistered(){
<<<<<<< HEAD
        CreatePatientRequest createPatientRequest = CreatePatientRequest.builder()
                .firstName("Rich")
                .lastName("Doe")
                .phoneNumber("08033456789")
                .email("rich@gmail.com")
                .nin("12345")
                .streetName("Yaba")
                .houseNumber("23")
                .state("Lagos")
                .country("Nigeria")
                .build();
        CreatePatientResponse createPatientResponse = patientService.createPatient(createPatientRequest);
        assertThat(createPatientResponse.getMessage()).isEqualTo("Patient " + createPatientRequest.getFirstName()
                + " " + createPatientRequest.getLastName() + " " + "Account Created Successfully");

=======
        assertThat(createPatientResponse.getPatientIdentificationNumber()).isNotNull();
        assertThat(createPatientResponse.getEReachUsername()).isInstanceOf(String.class);
>>>>>>> 6543a71c1d1f34cd34299ec0cc5258ad53402457
    }

    @Test
    void testThatNoRequiredFieldIsEmpty_ElseExceptionIsThrown() {
        assertThatThrownBy(() -> patientService.createPatient(buildPatientWithEmptyRequiredFields()))
                                               .isExactlyInstanceOf(NullPointerException.class);
    }

    @Test
    @SneakyThrows
    void testThatPatientIdentificationNumberIsSentToPatientImmediatelyAfterRegistration() {
        assertThat(createPatientResponse).isNotNull();
    }
    
    @Test void patientWhoAlreadyHasAnAccountTriesToRegisterAgain_RegistrationFailedExceptionIsThrown(){
    
    }
    @Test
    void testThatPatientReceivesEReachUsernameOnSuccessfulRegistration() {
    }
    
    @Test
    @SneakyThrows
    void testThatExceptionIsThrown_IfInvalidDataIsEntered() {
        assertThatThrownBy(()->patientService.createPatient(buildInvalidPatientData()))
                .isInstanceOf(EReachBaseException.class)
                .hasMessageContaining("Invalid Domain:: valid domain includes");
    }
<<<<<<< HEAD




=======
    
    
    
>>>>>>> 6543a71c1d1f34cd34299ec0cc5258ad53402457
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
                .state("Lagos")
                .country("Nigeria")
                .houseNumber("3435")
                .streetName("harvey road")
                .nin("ie1234")
                .phoneNumber("sD080234576")
                .build();
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
    
    public CreatePatientRequest buildPatientFieldsForEreachUsername(){
        return CreatePatientRequest.builder()
                       .firstName("John")
                       .lastName("Doe")
                       .email("johndoe@gmail.com")
                       .build();
    }
}
