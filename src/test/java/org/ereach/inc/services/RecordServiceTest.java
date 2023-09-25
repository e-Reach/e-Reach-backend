package org.ereach.inc.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.dtos.response.HospitalResponse;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.repositories.hospital.EReachRecordRepository;
//import org.ereach.inc.services.hospital.EReachRecordService;
import org.ereach.inc.services.hospital.EreachRecordService;
import org.ereach.inc.services.hospital.RecordService;
import org.ereach.inc.services.users.EreachHospitalAdminService;
import org.ereach.inc.services.users.HospitalAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
public class RecordServiceTest {
    @Autowired
    private RecordService recordService;

    private EreachRecordService ereachRecordService;

    private ModelMapper modelMapper;

    private EreachHospitalAdminService ereachHospitalAdminService;

    private EReachRecordRepository eReachRecordRepository;
    @BeforeEach
    void SetUp(){
    }

    @Test
    void testThatRecordCanBeCreated(){


        CreateRecordRequest createRecordRequest = new CreateRecordRequest();
        createRecordRequest.setOfficerIdentificationNumber("123");
        createRecordRequest.setPatientIdentificationNumber("234");
        CreateRecordResponse recordResponse = ereachRecordService.createRecord(createRecordRequest);
        log.info("Newly created record------->{}", recordResponse);
        assertThat(recordResponse).isNotNull();








    }

//    @Test
//    public void testThatRecordCanBeCreated(){
//        CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
//                                                          .build();
//        CreateRecordResponse createRecordResponse = recordService.createRecord(createRecordRequest);
//        assertThat(createRecordResponse.getMessage()).isEqualTo("Record created successfully");
//    }
//
//    @Test void testThatNewLogCanBeAddedToRecord(){
//        CreateRecordRequest createRecordRequest =
//                CreateRecordRequest.builder()
//                                   .centreCreated(null)
//                                   .dateCreated(LocalDate.from(LocalDate.of(2023,9,20)))
//                                   .lastTimeUpdated(LocalTime.from(LocalTime.MIN))
//                                   .build();
//    }
//
}
