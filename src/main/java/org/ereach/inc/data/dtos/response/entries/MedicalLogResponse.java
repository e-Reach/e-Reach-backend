package org.ereach.inc.data.dtos.response.entries;

import lombok.*;
import org.ereach.inc.data.dtos.response.entries.DoctorReportResponseDTO;
import org.ereach.inc.data.dtos.response.entries.PrescriptionsResponseDTO;
import org.ereach.inc.data.dtos.response.entries.TestResponseDTO;
import org.ereach.inc.data.dtos.response.entries.VitalsResponseDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalLogResponse {
	private String message;
	private LocalDate dateCreated;
	private LocalTime timeCreated;
	private String patientIdentificationNumber;
	private VitalsResponseDTO vitalsResponseDTO;
	private List<TestResponseDTO> testResponseDTO;
	private List<PrescriptionsResponseDTO> prescriptionsResponseDTO;
	private DoctorReportResponseDTO doctorReportResponseDTO;
	private String hospitalName;
	private String hospitalEmail;
}
