package org.ereach.inc.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HospitalAdminResponse {
	private String message;
	private String adminEmail;
	private String adminFirstName;
	private String adminLastName;
}
