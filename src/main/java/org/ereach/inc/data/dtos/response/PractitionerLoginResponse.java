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
	private String username;
	private String email;
	private String practitionerIdentificationNumber;
}
