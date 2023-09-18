package org.ereach.inc.data.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePractitionerRequest {
	private String workerIdentity;
	@NonNull
	@NotEmpty
	private String firstName;
	@NonNull
	@NotEmpty
	private String lastName;
	@NonNull
	@NotEmpty
	private String email;
	private String phoneNumber;
}
