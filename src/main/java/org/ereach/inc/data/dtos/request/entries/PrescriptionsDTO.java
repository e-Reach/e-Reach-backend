package org.ereach.inc.data.dtos.request.entries;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrescriptionsDTO {
	
	private String medicationName;
	private String dosage;
	private String dosageFrequency;
	private LocalDate startDate;
	private LocalDate prescriptionDate;
	private String practitionerEmail;
}
