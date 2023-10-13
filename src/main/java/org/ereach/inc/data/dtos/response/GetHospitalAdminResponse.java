package org.ereach.inc.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class GetHospitalAdminResponse {
	private String message;
	private String adminFirstName;
	private String adminLastName;
	private String adminEmail;
	private String adminPhoneNumber;
}
