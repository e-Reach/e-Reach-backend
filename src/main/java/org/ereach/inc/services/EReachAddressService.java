package org.ereach.inc.services;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ereach.inc.data.dtos.request.*;
import org.ereach.inc.data.dtos.response.*;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.repositories.EReachAddressRepository;
import org.ereach.inc.exceptions.EReachBaseException;
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

@Service
@AllArgsConstructor
public class EReachAddressService implements AddressService{
	private EReachAddressRepository addressRepository;
	private ModelMapper mapper;
	private ObjectMapper objectMapper;
	@Getter
	private static String testId;
	@Override
	public AddressCreationResponse saveAddress(AddressCreationRequest addressCreationRequest) {
		Address mappedAddress = mapper.map(addressCreationRequest, Address.class);
		Address savedAddress = addressRepository.save(mappedAddress);
		testId = savedAddress.getId();
		return mapper.map(savedAddress, AddressCreationResponse.class);
	}
	@Override
	public AddressUpdateResponse updateAddress(AddressUpdateRequest addressUpdateRequest) {
		Optional<Address> foundAddress = addressRepository.findById(addressUpdateRequest.getId());
		AtomicReference<AddressUpdateResponse> atomicReference = new AtomicReference<>();
		foundAddress.ifPresentOrElse(address -> {
			JsonPatch updatePatch = buildAddressUpdatePatch(addressUpdateRequest);
			try {
				AddressUpdateResponse response = applyPatchTo(address, updatePatch);
				atomicReference.set(response);
			} catch (JsonPatchException e) {
				throw new EReachUncheckedBaseException(e);
			}
		}, ()->{throw new EReachUncheckedBaseException("Address does not exist");});
		return atomicReference.get();
	}
	
	private JsonPatch buildAddressUpdatePatch(AddressUpdateRequest addressUpdateRequest) {
		Field[] fields = addressUpdateRequest.getClass().getDeclaredFields();
		List<ReplaceOperation> operations = Arrays.stream(fields)
				                          .filter(field -> filterEmptyFields(addressUpdateRequest, field))
				                          .map(field -> performReplaceOperation(addressUpdateRequest, field)).toList();
		List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
		return new JsonPatch(patchOperations);
	}
	
	private AddressUpdateResponse applyPatchTo(Address address, JsonPatch updatePatch) throws JsonPatchException {
		JsonNode convertedAddress = objectMapper.convertValue(address, JsonNode.class);
		JsonNode updatedNode = updatePatch.apply(convertedAddress);
		address = objectMapper.convertValue(updatedNode, Address.class);
		addressRepository.save(address);
		return mapper.map(address, AddressUpdateResponse.class) ;
	}
	
	private ReplaceOperation performReplaceOperation(AddressUpdateRequest addressUpdateRequest, Field field){
		field.setAccessible(true);
		String jsonPointerPath = "/"+field.getName();
		try {
			JsonPointer jsonPointer = new JsonPointer(jsonPointerPath);
			Object accessedField = field.get(addressUpdateRequest);
			TextNode textNode = new TextNode(accessedField.toString());
			return new ReplaceOperation(jsonPointer, textNode);
		} catch (Throwable exception) {
			throw new EReachUncheckedBaseException(exception);
		}
	}
	
	private boolean filterEmptyFields(AddressUpdateRequest addressUpdateRequest, Field field){
		field.setAccessible(true);
		try {
			return field.get(addressUpdateRequest) != null;
		} catch (IllegalAccessException exception) {
			throw new EReachUncheckedBaseException(exception);
		}
	}
	
	@Override
	public List<GetAddressResponse> getAllAddresses() {
		return addressRepository.findAll()
							    .stream()
							    .map(address -> mapper.map(address, GetAddressResponse.class))
							    .collect(Collectors.toList());
	}
	
	@Override
	public GetAddressResponse getAddressById(String id) {
		return null;
	}
	
	@Override
	public void deleteAll() {
	
	}
}
