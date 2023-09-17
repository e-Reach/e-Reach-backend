package org.ereach.inc.data.dtos.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AddressResponse {
	private String id;
	private String streetName;
	private String streetNumber;
	private String houseNumber;
	private String state;
	private String country;
	private String message;
}
