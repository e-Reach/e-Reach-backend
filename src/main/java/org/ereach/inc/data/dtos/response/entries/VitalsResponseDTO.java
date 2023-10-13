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
public class VitalsResponseDTO {
	
	private LocalDate dateTaken;
	private double bloodPressure;
	private double heartRate;
	private double temperature;
	private double respiratoryRate;
	private String officerEmail;
}
