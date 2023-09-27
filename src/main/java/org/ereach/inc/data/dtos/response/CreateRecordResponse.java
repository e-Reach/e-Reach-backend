package org.ereach.inc.data.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter

public class CreateRecordResponse {
    private String id;
    private String message;
    private String patientIdentificationNumber;
    private LocalDate dateCreated;
    private LocalTime lastTimeUpdated;

}
