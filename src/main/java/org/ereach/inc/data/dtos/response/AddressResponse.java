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
	private String streetAddress;
	private String postalCode;
	private String houseNumber;
	private String state;
	private String country;
	private String message;
}
