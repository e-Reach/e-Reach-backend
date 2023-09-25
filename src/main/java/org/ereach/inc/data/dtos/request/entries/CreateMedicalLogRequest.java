package org.ereach.inc.data.dtos.request.entries;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Data
public class CreateMedicalLogRequest {
    private DoctorReportDTO doctorReportDTO;
    private List<PrescriptionsDTO> prescriptionsDTO;
    private List<TestDTO> testDTO;
    private VitalsDTO vitalsDTO;
    private String patientIdentificationNumber;
    private String hospitalEmail;
}
