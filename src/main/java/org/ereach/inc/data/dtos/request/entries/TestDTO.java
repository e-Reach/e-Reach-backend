package org.ereach.inc.data.dtos.request.entries;

import jakarta.persistence.OneToOne;
import lombok.*;
import org.ereach.inc.data.models.entries.TestResult;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDTO {
	
	private String testName;
	private TestResult testResult;
	private LocalDate testDate;
	private String practitionerEmail;
}
