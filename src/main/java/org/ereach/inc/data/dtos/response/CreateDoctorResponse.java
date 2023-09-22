package org.ereach.inc.data.dtos.response;

import lombok.Getter;
import lombok.Setter;
import org.ereach.inc.data.models.Role;

@Setter
@Getter
public class CreateDoctorResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;
    private String message;
}
