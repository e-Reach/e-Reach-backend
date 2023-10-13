package org.ereach.inc.data.dtos.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PractitionerLoginRequest {
	
	private String role;
	private String email;
	private String practitionerIdentificationNumber;
}
