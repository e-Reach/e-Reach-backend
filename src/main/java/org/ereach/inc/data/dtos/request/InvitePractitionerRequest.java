package org.ereach.inc.data.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class InvitePractitionerRequest {
	
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
