package org.ereach.inc.data.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.ereach.inc.data.models.annotations.ValidDomain;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateHospitalRequest {
	
//	@NotEmpty
//	@NonNull
	private String HEFAMAA_ID;
	@NotEmpty
	@NonNull
	private String hospitalName;
	@NotEmpty
	@NonNull
	@Email(message = "Please enter a valid email format",
			regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
	@ValidDomain(domains = {"gmail.com", "yahoo.com", "outlook.com", "semicolon.africa.com", "hotmail.com", "hotmail.co.uk", "freenet.de"})
	@Valid
	private String hospitalEmail;
	@NotEmpty
	@NonNull
	private String hospitalPhoneNumber;
	@NotEmpty
	@NonNull
	private String adminEmail;
	@NotEmpty
	@NonNull
	private String adminFirstName;
	@NotEmpty
	@NonNull
	private String adminLastName;
	@NotEmpty
	@NonNull
	private String adminPhoneNumber;
	@NotEmpty
	@NonNull
	private String streetName;
	@NotEmpty
	@NonNull
	private String streetNumber;
	@NotEmpty
	@NonNull
	private String state;
}
