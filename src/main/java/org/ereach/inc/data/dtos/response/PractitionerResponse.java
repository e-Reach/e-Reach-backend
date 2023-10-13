package org.ereach.inc.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
public class PractitionerResponse {
	private String message;
	private String email;
	private String practitionerIdentificationNumber;
}
