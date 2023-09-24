package org.ereach.inc.data.dtos.request;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class CreateMedicalLogRequest {

    private String patientIdentificationNumber;
    private String hospitalEmail;
}
