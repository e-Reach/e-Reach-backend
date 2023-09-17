package org.ereach.inc.data.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder

public class CreatePatientRequest {

    @NotBlank
    @Valid
    private String firstName;
    @NotBlank
    @Valid
    private String lastName;
    @NotBlank
    @Valid
    private String nin;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Valid
    private String email;
}
