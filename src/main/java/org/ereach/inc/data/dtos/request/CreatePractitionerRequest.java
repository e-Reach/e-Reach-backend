package org.ereach.inc.data.dtos.request;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreatePractitionerRequest {
	private String role;
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
	private String hospitalEmail;
}
