package org.ereach.inc.data.dtos.request.entries;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorReportDTO {
	
	private String reportContent;
	private LocalDateTime date;
	private String diagnosis;
	private String treatment;
	private String practitionerEmail;
}
