package org.ereach.inc.data.dtos.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AddressCreationRequest {
	private String postalCode;
	private String streetAddress;
	private String nameOfStreet;
	private String numberOfStreet;
	private String state;
	private String country;
}
