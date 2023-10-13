package org.ereach.inc.data.dtos.response;

import com.fasterxml.jackson.core.StreamReadConstraints;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.ereach.inc.data.models.AccountStatus;
import org.ereach.inc.data.models.Role;
import org.hibernate.annotations.NaturalId;

import static jakarta.persistence.EnumType.STRING;


@AllArgsConstructor
@Data
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PractitionerResponse {
	
	private String message;
	private String email;
	private String practitionerIdentificationNumber;
	private String firstName;
	private String lastName;
	private String phoneNumber;
}
