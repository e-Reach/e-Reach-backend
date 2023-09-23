package org.ereach.inc.data.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@SuppressWarnings("/unchecked/")
public class InvitePractitionerRequest {
	
	private String firstName;
	@NonNull
	@NotEmpty
	private String lastName;
	@NonNull
	@NotEmpty
	private String email;
	private String role;
	private String phoneNumber;
}
