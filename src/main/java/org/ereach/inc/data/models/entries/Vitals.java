package org.ereach.inc.data.models.entries;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Vitals extends Entry{
	
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
