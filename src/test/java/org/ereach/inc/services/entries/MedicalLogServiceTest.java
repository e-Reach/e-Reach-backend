package org.ereach.inc.services.entries;

import lombok.SneakyThrows;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.CreateMedicalLogRequest;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.dtos.response.MedicalLogResponse;
import org.ereach.inc.services.InMemoryDatabase;
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.services.hospital.RecordService;
import org.ereach.inc.services.users.HospitalAdminService;
import org.ereach.inc.services.users.PatientService;
import org.ereach.inc.utilities.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MedicalLogServiceTest {
    
    @Autowired
    private MedicalLogService medicalLogService;
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalAdminService hospitalAdminService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private InMemoryDatabase inMemoryDatabase;
    @Autowired
    private PatientService patientService;
    
    @Test
    @SneakyThrows
    public void createNewMedicalLogTest() {
        hospitalAdminService.registerHospital(buildCreateHospitalRequest());
        HospitalResponse hospitalResponse = hospitalService.saveHospitalPermanently(JWTUtil.getTestToken());
        hospitalAdminService.saveHospitalAdminPermanently(JWTUtil.getTestToken());
        CreatePatientResponse response = patientService.createPatient(buildPatient());
        System.out.println("In test class it was: "+response.getPatientIdentificationNumber());
        recordService.createRecord(buildRecordRequest(hospitalResponse, response.getPatientIdentificationNumber()));
        MedicalLogResponse medicalLogResponse = medicalLogService.createNewLog(buildCreateMedicalLogRequest(hospitalResponse, response.getPatientIdentificationNumber()));
        
        assertThat(medicalLogResponse).isNotNull();
        assertThat(medicalLogResponse.getTimeCreated()).isNotNull();
        assertThat(medicalLogResponse.getMessage()).isNotNull();
        assertThat(medicalLogResponse.getDateCreated()).isEqualTo(LocalDate.now());
    }
    
    @Test
    void medicalLogsCanBeFetched_UsingTheEmailOfTheHospitalCreatedAt() {
    
    }
    
    @Test
    void addMedicalLog_MedicalLogIsFetchedByPatientIdentificationNumber() {
    
    }
    
    @Test
    void deactivateMedicalLogByMidnightTest() {
    
    }
    
    @Test
    void deactivateMedicalLog_LogsWhosePatientsAreAdmissionAreNotDeactivated() {
    
    }
    
    private static CreateRecordRequest buildRecordRequest(HospitalResponse hospitalResponse, String PIN) {
        return CreateRecordRequest.builder()
                       .officerIdentificationNumber("")
                       .patientIdentificationNumber(PIN)
                       .hospitalEmail(hospitalResponse.getHospitalEmail())
                       .build();
    }
    
    private CreateMedicalLogRequest buildCreateMedicalLogRequest(HospitalResponse hospitalResponse, String patientIdentificationNumber) {
        return CreateMedicalLogRequest.builder()
                       .hospitalEmail(hospitalResponse.getHospitalEmail())
                       .patientIdentificationNumber(patientIdentificationNumber)
                       .build();
    }
    
    private CreateHospitalRequest buildCreateHospitalRequest() {
        return CreateHospitalRequest.builder()
                       .hospitalName("Glory Be To God Hospital")
                       .adminEmail("glory@yahoo.com")
                       .hospitalEmail("alaabdulmalik03@gmail.com")
                       .adminFirstName("Glory")
                       .adminLastName("Oyedotun")
                       .HEFAMAA_ID("Brenda")
                       .hospitalPhoneNumber("07036174617")
                       .adminPhoneNumber("08023677114")
                       .state("Lagos")
                       .streetNumber("342B")
                       .streetName("herbert Macaulay way")
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
}