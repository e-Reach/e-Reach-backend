package org.ereach.inc.data.dtos.response;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetPractitionerResponse {
	private String message;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String practitionerIdentificationNumber;
}
