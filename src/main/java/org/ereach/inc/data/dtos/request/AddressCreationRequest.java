package org.ereach.inc.data.dtos.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class AddressCreationRequest {
	private String houseNumber;
	private String street;
	private String state;
	private String country;
}
