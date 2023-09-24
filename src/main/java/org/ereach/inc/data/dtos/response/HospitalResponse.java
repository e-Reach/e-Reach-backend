package org.ereach.inc.data.dtos.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

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
}
