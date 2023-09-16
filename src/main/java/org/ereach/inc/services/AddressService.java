package org.ereach.inc.services;
import org.ereach.inc.data.dtos.request.*;
import org.ereach.inc.data.dtos.response.*;

import java.util.List;

public interface AddressService {
	
	AddressCreationResponse saveAddress(AddressCreationRequest addressCreationRequest);
	AddressUpdateResponse updateAddress(AddressUpdateRequest addressUpdateRequest);
	List<GetAddressResponse> getAllAddresses();
	GetAddressResponse getAddressById(String id);
	void deleteAll();
}
