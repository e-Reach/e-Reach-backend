package org.ereach.inc.data.dtos.response.entries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionsResponseDTO {
	
	private String medicationName;
	private String dosage;
	private String dosageFrequency;
	private LocalDate startDate;
	private LocalDate prescriptionDate;
	private String practitionerEmail;
}
