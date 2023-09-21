package org.ereach.inc.services;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.services.hospital.EReachRecordService;
import org.ereach.inc.services.hospital.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RecordServiceTest {
    @Autowired
    private RecordService recordService;

    @Test
    public void testThatRecordCanBeCreated(){
        CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
                                                          .build();
        CreateRecordResponse createRecordResponse = recordService.createRecord(createRecordRequest);
        assertThat(createRecordResponse.getMessage()).isEqualTo("Record created successfully");
    }

    @Test void testThatNewLogCanBeAddedToRecord(){
        CreateRecordRequest createRecordRequest =
                CreateRecordRequest.builder()
                                   .centreCreated(null)
                                   .dateCreated(LocalDate.from(LocalDate.of(2023,9,20)))
                                   .lastTimeUpdated(LocalTime.from(LocalTime.MIN))
                                   .build();
    }
    
}
