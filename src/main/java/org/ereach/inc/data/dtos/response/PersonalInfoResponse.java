package org.ereach.inc.data.dtos.response;

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
public class PersonalInfoResponse {
	
	private String id;
	private String houseNumber;
	private String streetName;
	private String streetNumber;
	private String state;
	private String country;
	private String email;
	private String phoneNumber;
	private String allergy;
	private String knownHealthConditions;
	private LocalDate dateOfBirth;
}
