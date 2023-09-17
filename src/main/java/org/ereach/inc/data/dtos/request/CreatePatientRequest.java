package org.ereach.inc.data.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientRequest {

    @NotEmpty
    @NonNull
    private String firstName;
    @NonNull
    @NotEmpty
    private String lastName;
    @NonNull
    @NotEmpty
    private String nin;
    @NotEmpty
    @NonNull
    private String phoneNumber;
    @NotEmpty
    @NonNull
    @Email(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @NonNull
    @NotEmpty
    private String houseNumber;
    @NonNull
    @NotEmpty
    private String streetName;
    @NotEmpty
    @NonNull
    private String state;
    @NonNull
    @NotEmpty
    private String country;
}
