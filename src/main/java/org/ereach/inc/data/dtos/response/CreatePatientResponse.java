package org.ereach.inc.data.dtos.response;

import lombok.*;

@Setter
@Getter

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String patientIdentificationNumber;
    private String eReachUsername;
    private String message;
}
