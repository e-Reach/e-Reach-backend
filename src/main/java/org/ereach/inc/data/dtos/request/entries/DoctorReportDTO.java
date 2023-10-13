package org.ereach.inc.data.dtos.request.entries;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorReportDTO {
	
	private LocalDateTime date;
	private String reportContent;
	private String diagnosis;
	private String treatment;
	private String practitionerEmail;
}
