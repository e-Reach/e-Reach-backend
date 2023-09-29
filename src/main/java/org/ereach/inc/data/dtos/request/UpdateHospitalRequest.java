package org.ereach.inc.data.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateHospitalRequest {
	private MultipartFile logo;
	private String hospitalEmail;
	private String HEFAMAA_ID;
	private String hospitalName;
	private String hospitalPhoneNumber;
	private AddressUpdateRequest addressUpdateRequest;
}
