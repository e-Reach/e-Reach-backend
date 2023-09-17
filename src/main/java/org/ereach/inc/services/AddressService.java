package org.ereach.inc.services;
import org.ereach.inc.data.dtos.request.*;
import org.ereach.inc.data.dtos.response.*;

import java.util.List;

public interface AddressService {
	
	AddressResponse saveAddress(AddressCreationRequest addressCreationRequest);
	AddressResponse updateAddress(AddressUpdateRequest addressUpdateRequest);
	List<AddressResponse> getAllAddresses();
	AddressResponse getAddressById(String id);
	void deleteAll();
}
