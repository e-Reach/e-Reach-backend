package org.ereach.inc.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HospitalResponse {
	private String message;
	private String HEFAMAA_ID;
	private String hospitalName;
	private String hospitalPhoneNumber;
	private String hospitalEmail;
	private String logoCloudUrl;

}
