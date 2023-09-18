package org.ereach.inc.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ereach.inc.data.dtos.request.AddressCreationRequest;
import org.ereach.inc.data.dtos.request.CreatePersonalInfoRequest;
import org.ereach.inc.data.dtos.request.UpdatePersonalInfoRequest;
import org.ereach.inc.data.dtos.response.AddressResponse;
import org.ereach.inc.data.dtos.response.PersonalInfoResponse;
import org.ereach.inc.data.models.Address;
import org.ereach.inc.data.models.PersonalInfo;
import org.ereach.inc.data.repositories.EReachPersonalInfoRepository;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.apache.catalina.util.Introspection.getDeclaredFields;
import static org.ereach.inc.utilities.AppUtil.doReplace;
import static org.ereach.inc.utilities.AppUtil.filterEmptyField;

@Service
@AllArgsConstructor
@Slf4j
public class EReachPersonalInfoService implements PersonalInfoService {
	private EReachPersonalInfoRepository personalInfoRepo;
	private AddressService addressService;
	private ModelMapper modelMapper;
	private ObjectMapper objectMapper;
	@Getter
	private static String testId;
	
	@Override
	public PersonalInfoResponse addPersonalInfo(CreatePersonalInfoRequest personalInfoRequest) {
		PersonalInfo mappedPersonalInfo = modelMapper.map(personalInfoRequest, PersonalInfo.class);
		AddressCreationRequest addressRequest = modelMapper.map(personalInfoRequest, AddressCreationRequest.class);
		AddressResponse savedAddress = addressService.saveAddress(addressRequest);
		Address address = modelMapper.map(savedAddress, Address.class);
		mappedPersonalInfo.setAddress(address);
		PersonalInfo savedPersonalInfo = personalInfoRepo.save(mappedPersonalInfo);
		testId = savedPersonalInfo.getId();
		return modelMapper.map(savedPersonalInfo, PersonalInfoResponse.class);
	}
	
	@Override
	public PersonalInfoResponse updatePersonalInfo(UpdatePersonalInfoRequest personalInfoRequest) {
		Optional<PersonalInfo> foundPersonalInfo = personalInfoRepo.findById(personalInfoRequest.getId());
		AtomicReference<PersonalInfoResponse> atomicReference = new AtomicReference<>();
		foundPersonalInfo.ifPresentOrElse(personalInfo -> {
			JsonPatch jsonPatch = buildUpdatePatch(personalInfoRequest);
			try {
				PersonalInfoResponse response = applyPatchTo(personalInfo, jsonPatch);
				atomicReference.set(response);
			} catch (JsonPatchException e) {
				throw new EReachUncheckedBaseException(e);
			}
		}, ()->{throw new EReachUncheckedBaseException("");});
		return atomicReference.get();
	}
	
	private JsonPatch buildUpdatePatch(UpdatePersonalInfoRequest personalInfoRequest) {
		List<ReplaceOperation> operations = Arrays.stream(getDeclaredFields(UpdatePersonalInfoRequest.class))
				                                  .filter(field -> filterEmptyField(personalInfoRequest, field))
				                                  .map(field -> doReplace(personalInfoRequest, field))
				                                  .toList();
		List<JsonPatchOperation> replaceOperationList = new ArrayList<>(operations);
		return new JsonPatch(replaceOperationList);
	}
	
	private PersonalInfoResponse applyPatchTo(PersonalInfo personalInfo, JsonPatch jsonPatch) throws JsonPatchException {
		JsonNode convertedPersonalInfo = objectMapper.convertValue(personalInfo, JsonNode.class);
		JsonNode updatedPersonalInfo = jsonPatch.apply(convertedPersonalInfo);
		personalInfo = objectMapper.convertValue(updatedPersonalInfo, PersonalInfo.class);
		personalInfoRepo.save(personalInfo);
		return modelMapper.map(personalInfo, PersonalInfoResponse.class);
	}
	
	@Override
	public PersonalInfoResponse getPersonalInfoById(String infoId) {
		return null;
	}
	
	@Override
	public void deleteById(String id) {
	
	}
	
	@Override
	public void deleteAll() {
	
	}
}
