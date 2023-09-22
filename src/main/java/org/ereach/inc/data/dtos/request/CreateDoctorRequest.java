package org.ereach.inc.data.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.ereach.inc.data.models.Role;
@Setter
@Getter
public class CreateDoctorRequest {
    @NotEmpty
    @NonNull
    private String firstName;
    @NotEmpty
    @NonNull
    private String lastName;
    @NonNull
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @NotEmpty
    @NonNull
    private String phoneNumber;
    @NotEmpty
    @NonNull
    private Role role;
}
