package org.ereach.inc.data.dtos.response.entries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorReportResponseDTO {
	
	private LocalDateTime date;
	private String reportContent;
	private String diagnosis;
	private String treatment;
	private String practitionerEmail;
}
