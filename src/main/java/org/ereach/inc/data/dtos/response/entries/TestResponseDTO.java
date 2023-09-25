package org.ereach.inc.data.dtos.response.entries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResponseDTO {
	
	private String testName;
	private String fileUrl;
	private LocalDate testDate;
	private String testReport;
	private String practitionerEmail;
}
