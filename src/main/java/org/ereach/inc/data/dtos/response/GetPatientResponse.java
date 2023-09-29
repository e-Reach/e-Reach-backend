package org.ereach.inc.data.dtos.response;

import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.ereach.inc.data.models.PersonalInfo;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.hospital.Record;

import static jakarta.persistence.EnumType.STRING;

@Getter
@Setter
public class GetPatientResponse {
    
    private String email;
    private String phoneNumber;
    private String eReachUsername;
    private String firstName;
    private String lastName;
    private String patientIdentificationNumber;
}
