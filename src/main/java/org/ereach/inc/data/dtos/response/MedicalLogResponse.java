package org.ereach.inc.data.dtos.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalLogResponse {
	private String message;
	private LocalDate dateCreated;
	private LocalTime timeCreated;
	private String patientIdentificationNumber;
	private String hospitalEmail;
}
