package org.ereach.inc.data.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePersonalInfoRequest {
	
	@NotBlank
	@Valid
	private String houseNumber;
	@NotBlank
	private String streetName;
	@NotBlank
	private String streetNumber;
	@NotBlank
	private String state;
	@NotBlank
	private String country;
	@NotBlank
	private String email;
	@NotBlank
	private String phoneNumber;
	@NotBlank
	private String allergy;
	@NotBlank
	private String knownHealthConditions;
	@NotBlank
	private LocalDate dateOfBirth;
	
}
