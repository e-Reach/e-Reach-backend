package org.ereach.inc.data.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePharmacistRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
