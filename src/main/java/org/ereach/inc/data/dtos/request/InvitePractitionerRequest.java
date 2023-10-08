package org.ereach.inc.data.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InvitePractitionerRequest {
	
	@NotNull
	@NotEmpty
	private String firstName;
	@NonNull
	@NotEmpty
	private String lastName;
	@NonNull
	@NotEmpty
	private String email;
	@NonNull
	@NotEmpty
	private String role;
	@NonNull
	@NotEmpty
	private String hospitalEmail;
	private String phoneNumber;
}
