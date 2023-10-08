package org.ereach.inc.services.entries;

import lombok.SneakyThrows;
import org.ereach.inc.data.dtos.request.CreateHospitalRequest;
import org.ereach.inc.data.dtos.request.entries.*;
import org.ereach.inc.data.dtos.request.CreatePatientRequest;
import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreatePatientResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.ereach.inc.services.InMemoryDatabase;
import org.ereach.inc.services.hospital.HospitalService;
import org.ereach.inc.services.hospital.RecordService;
import org.ereach.inc.services.users.HospitalAdminService;
import org.ereach.inc.services.users.PatientService;
import org.ereach.inc.utilities.JWTUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
    HospitalResponse hospitalResponse;
    CreatePatientResponse response;
    MedicalLogResponse medicalLogResponse;
    
    @BeforeEach
    @SneakyThrows
    void startEachTestWith(){
        hospitalAdminService.registerHospital(buildCreateHospitalRequest());
        hospitalResponse = hospitalService.saveHospitalPermanently(JWTUtil.getTestToken());
        hospitalAdminService.saveHospitalAdminPermanently(JWTUtil.getTestToken());
        response = patientService.createPatient(buildPatient());
        recordService.createRecord(buildRecordRequest(hospitalResponse, response.getPatientIdentificationNumber()));
        medicalLogResponse = medicalLogService.createNewLog(buildCreateMedicalLogRequest(hospitalResponse, response.getPatientIdentificationNumber()));
    }
    
    @Test
    @Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void createNewMedicalLogTest() {
        assertThat(medicalLogResponse).isNotNull();
        assertThat(medicalLogResponse.getTimeCreated()).isNotNull();
        assertThat(medicalLogResponse.getMessage()).isNotNull();
        assertThat(medicalLogResponse.getDateCreated()).isEqualTo(LocalDate.now());
    }
    
    @Test void testThatTheMedicalLogSavedHasARecordThatItIsSavedInto(){
    
    }
    
    @Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test void testThatTheMedicalLogSavedHasAReferenceToTheHospitalItWasCreated(){
        assertThat(medicalLogResponse.getHospitalName()).isNotNull();
        assertThat(medicalLogResponse.getHospitalEmail()).isNotNull();
    }
    
    
    
    @Test
    @SneakyThrows
    @Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void testThatTheSystemAutomaticallyUploadsAFileToTheCloud_IfTheMedicalLogContainsAFile(){
        medicalLogService.createNewLog(buildCreateMedicalLogRequest(hospitalResponse, response.getPatientIdentificationNumber()));
    }
    @Test
    @Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void addMedicalLog_MedicalLogIsFetchedByPatientIdentificationNumber() throws RequestInvalidException {
        List<MedicalLogResponse> logResponses = medicalLogService.viewPatientMedicalLogs("1ca5634bd5");
        MedicalLogResponse logResponse = medicalLogService.viewPatientMedicalLog("7f64481934", LocalDate.of(2023, 9, 24));
        logResponses.forEach(medicalLogResponse -> {
            assertThat(medicalLogResponse).isNotNull();
            assertThat(medicalLogResponse.getPatientIdentificationNumber()).isNotNull();
        });
        assertThat(logResponse).isNotNull();
        assertThat(logResponse.getPatientIdentificationNumber()).isNotNull();
    }
    
    @Test
    void deactivateMedicalLogByMidnightTest() {
        
    }
    
    @Test
    void deactivateMedicalLog_LogsWhosePatientsAreAdmissionAreNotDeactivated() {
    
    }
    
    private static CreateRecordRequest buildRecordRequest(HospitalResponse hospitalResponse, String PIN) {
        return CreateRecordRequest.builder()
                       .patientIdentificationNumber(PIN)
                       .hospitalEmail(hospitalResponse.getHospitalEmail())
                       .build();
    }
    
    private CreateMedicalLogRequest buildCreateMedicalLogRequest(HospitalResponse hospitalResponse, String patientIdentificationNumber) throws IOException {
        return CreateMedicalLogRequest.builder()
                       .hospitalEmail(hospitalResponse.getHospitalEmail())
                       .testDTO(List.of(TestDTO.builder()
                                                .testName("HIV")
                                                .multipartFile(buildMultipartFile())
                                                .testDate(LocalDate.now()).build()))
                       .doctorReportDTO(DoctorReportDTO.builder().reportContent("hello").build())
                       .vitalsDTO(VitalsDTO.builder().bloodPressure(34.6).dateTaken(LocalDate.now()).build())
                       .prescriptionsDTO(List.of(PrescriptionsDTO.builder().medicationName("PCM").build()))
                       .patientIdentificationNumber(patientIdentificationNumber)
                       .build();
    }
    
    private MultipartFile buildMultipartFile() throws IOException {
        Path path = Path.of("C:\\Users\\USER\\IdeaProjects\\e-Reach\\src\\test\\resources\\e-reach-low-resolution-logo-black-on-white-background.png");
        try(InputStream inputStream = Files.newInputStream(path)) {
            return new MockMultipartFile("testImage", inputStream);
        }
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
                       .postalCode("342B")
                       .streetAddress("herbert Macaulay way")
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
                       .hospitalEmail("alaabdulmalik03@gmail.com")
                       .build();
    }
}