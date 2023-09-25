package org.ereach.inc.data.dtos.response.entries;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter

public class CreateRecordResponse {
    private String id;
    private String message;
    private String patientIdentificationNumber;
    private LocalDate dateCreated;
    private LocalTime lastTimeUpdated;
    private VitalsResponseDTO vitalsResponseDTO;
    private List<TestResponseDTO> testResponseDTO;
    private List<PrescriptionsResponseDTO> prescriptionsResponseDTO;
    private DoctorReportResponseDTO doctorReportResponseDTO;
}
