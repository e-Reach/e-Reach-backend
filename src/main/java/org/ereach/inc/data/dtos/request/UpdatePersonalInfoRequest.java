package org.ereach.inc.data.dtos.request;

import jakarta.annotation.Nullable;
import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdatePersonalInfoRequest {
	@NonNull
	private String id;
	@Nullable
	private String houseNumber;
	@Nullable
	private String streetName;
	@Nullable
	private String streetNumber;
	@Nullable
	private String state;
	@Nullable
	private String country;
	@Nullable
	private String email;
	@Nullable
	private String phoneNumber;
	@Nullable
	private String allergy;
	@Nullable
	private String knownHealthConditions;
	@Nullable
	private LocalDate dateOfBirth;
}
