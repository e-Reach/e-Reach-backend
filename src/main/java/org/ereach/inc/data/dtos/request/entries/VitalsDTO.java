package org.ereach.inc.data.dtos.request.entries;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VitalsDTO {
	
	private LocalDate dateTaken;
	private double bloodPressure;
	private double heartRate;
	private double temperature;
	private double respiratoryRate;
	private String officerEmail;
}
