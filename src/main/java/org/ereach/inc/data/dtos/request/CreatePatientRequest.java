package org.ereach.inc.data.dtos.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.ereach.inc.data.models.PersonalInfo;
import org.ereach.inc.data.models.Role;
import org.ereach.inc.data.models.hospital.Record;


@Setter
@Getter
@Builder

public class CreatePatientRequest {

    private String id;
    private String firstName;
    private String lastName;
    private String patientIdentificationNumber;
    private String nin;
    private Record record;
    private Role role;
    private PersonalInfo personalInfo;
    private String email;
    private String eReachUsername;

}
