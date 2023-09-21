package org.ereach.inc.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.AllArgsConstructor;
import org.ereach.inc.data.dtos.request.AddressCreationRequest;
import org.ereach.inc.data.dtos.request.AddressUpdateRequest;
import org.ereach.inc.data.dtos.response.AddressResponse;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.repositories.EReachAddressRepository;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static org.apache.catalina.util.Introspection.getDeclaredFields;
import static org.ereach.inc.utilities.AppUtil.*;

@Service
@AllArgsConstructor

public class EReachAddressService implements AddressService{
	private EReachAddressRepository addressRepository;
	private ModelMapper mapper;
	private ObjectMapper objectMapper;
	private static String testId;
	@Override
	public AddressResponse saveAddress(AddressCreationRequest addressCreationRequest) {
		Address mappedAddress = mapper.map(addressCreationRequest, Address.class);
		Address savedAddress = addressRepository.save(mappedAddress);
		testId = savedAddress.getId();
		return mapper.map(savedAddress, AddressResponse.class);
	}
	@Override
	public AddressResponse updateAddress(AddressUpdateRequest addressUpdateRequest) {
		Optional<Address> foundAddress = addressRepository.findById(addressUpdateRequest.getId());
		AtomicReference<AddressResponse> atomicReference = new AtomicReference<>();
		foundAddress.ifPresentOrElse(address -> {
			JsonPatch updatePatch = buildAddressUpdatePatch(addressUpdateRequest);
			try {
				AddressResponse response = applyPatchTo(address, updatePatch);
				atomicReference.set(response);
			} catch (JsonPatchException e) {
				throw new EReachUncheckedBaseException(e);
			}
		}, ()->{throw new EReachUncheckedBaseException("Address does not exist");});
		return atomicReference.get();
	}
	
	private JsonPatch buildAddressUpdatePatch(AddressUpdateRequest addressUpdateRequest) {
		List<ReplaceOperation> operations = Arrays.stream(getDeclaredFields(AddressUpdateRequest.class))
				                          .filter(field -> filterEmptyField(addressUpdateRequest, field))
				                          .map(field -> doReplace(addressUpdateRequest, field)).toList();
		List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
		return new JsonPatch(patchOperations);
	}
	
	
	private AddressResponse applyPatchTo(Address address, JsonPatch updatePatch) throws JsonPatchException {
		JsonNode convertedAddress = objectMapper.convertValue(address, JsonNode.class);
		JsonNode updatedNode = updatePatch.apply(convertedAddress);
		address = objectMapper.convertValue(updatedNode, Address.class);
		addressRepository.save(address);
		return mapper.map(address, AddressResponse.class) ;
	}
	
	@Override
	public List<AddressResponse> getAllAddresses() {
		return addressRepository.findAll()
							    .stream()
							    .map(address -> mapper.map(address, AddressResponse.class))
							    .collect(Collectors.toList());
	}
	
	@Override
	public AddressResponse getAddressById(String id) {
		return null;
	}
	
	@Override
	public void deleteAll() {
	
	}

	public static String getTestId(){
		return testId;
	}
}
