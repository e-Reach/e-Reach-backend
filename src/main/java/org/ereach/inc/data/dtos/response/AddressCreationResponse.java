package org.ereach.inc.data.dtos.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressCreationResponse {
	private String  id;
	private String houseNumber;
	private String streetName;
	private String streetNumber;
	private String state;
	private String country;
}
