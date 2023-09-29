package org.ereach.inc.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ereach.inc.data.dtos.response.entries.MedicalLogResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetRecordResponse {
	private String message;
	private String patientIdentificationNumber;
	private LocalDate dateCreated;
	private LocalTime lastTimeUpdated;
	private List<MedicalLogResponse> medicalLogResponses;
}
