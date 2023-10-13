package org.ereach.inc.data.dtos.response;

import lombok.*;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetRecordResponse {

    private String id;
    private LocalDate dateCreated;
    private LocalTime timeCreated;
    private boolean isActive;
	private String message;
	private String patientIdentificationNumber;
	private LocalTime lastTimeUpdated;
	private List<MedicalLogResponse> medicalLogResponses;
}
