package org.ereach.inc.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PractitionerLoginResponse {
	
	private String message;
	private String lastName;
	private String firstName;
	private String phoneNumber;
	private String role;
	private String email;
	private String practitionerIdentificationNumber;
}
