package org.ereach.inc.data.dtos.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddressUpdateRequest {
	private String id;
	private String street;
	private String houseNumber;
	private String state;
	private String country;
}
