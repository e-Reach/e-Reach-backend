package org.ereach.inc.services.entries;

import org.ereach.inc.data.dtos.request.CreateRecordRequest;
import org.ereach.inc.data.dtos.response.CreateRecordResponse;
import org.ereach.inc.services.hospital.RecordService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest

public class RecordServiceTest {
    private RecordService recordService;

    @Test
    public void testThatRecordCanBeCreated(){
        CreateRecordRequest createRecordRequest = CreateRecordRequest.builder()
                                                          .build();
        CreateRecordResponse createRecordResponse = recordService.createRecord(createRecordRequest);
        assertThat(createRecordResponse.getMessage()).isEqualTo("Record created successfully");
    }

    @Test void testThatNewLogCanBeAddedToRecord(){
    
    }
    
}
