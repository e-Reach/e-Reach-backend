package org.ereach.inc.services;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.data.dtos.response.GetRecordResponse;
import org.ereach.inc.data.models.PersonalInfo;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.hospital.Record;
import org.ereach.inc.services.hospital.MedicalRecordService;
import org.ereach.inc.services.hospital.RecordService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.TemporalAccessor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest

public class RecordServiceTest {
    private RecordService recordService;

    @Test
    public void testThatRecordCanBeCreated(){
        Hospital hospital = null;
        LocalDateTime fixedDateTime = LocalDateTime.of(2023, Month.JANUARY, 15, 10, 0);
        CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
                .centreCreated(null)
                .dateCreated(LocalDate.from(fixedDateTime))
                .lastTimeUpdated(LocalTime.from(fixedDateTime))
                .build();
        CreateRecordResponse createRecordResponse = recordService.createRecord(createRecordRequest);
        assertThat(createRecordResponse.getMessage()).isEqualTo("Record created successfully");
    }

    @Test void testThatNewLogCanBeAddedToRecord(){

        MedicalRecordService medicalRecordService = new MedicalRecordService(){
            CreateRecordRequest createRecordRequest =
                             CreateRecordRequest.builder()
                            .centreCreated(null)
                            .dateCreated(LocalDate.from(LocalDate.of(2023,9,20)))
                            .lastTimeUpdated(LocalTime.from(LocalTime.MIN))
                            .build();
            CreateRecordResponse createRecordResponse = recordService.addNewLogToRecord(createRecordRequest.getId(),)


        };

    }
}
