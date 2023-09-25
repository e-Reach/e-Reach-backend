package org.ereach.inc.data.models.entries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Vitals {
	
	@Id
	@GeneratedValue(strategy = UUID)
	private String id;
	private LocalDate dateTaken;
	private double bloodPressure;
	private double heartRate;
	private double temperature;
	private double respiratoryRate;
	private String officerEmail;
}
