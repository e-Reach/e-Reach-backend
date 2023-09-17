package org.ereach.inc.services;

import org.ereach.inc.data.dtos.request.CreatePersonalInfoRequest;
import org.ereach.inc.data.dtos.request.UpdatePersonalInfoRequest;
import org.ereach.inc.data.dtos.response.PersonalInfoResponse;

public interface PersonalInfoService {
	
	PersonalInfoResponse addPersonalInfo(CreatePersonalInfoRequest personalInfoRequest);
	PersonalInfoResponse updatePersonalInfo(UpdatePersonalInfoRequest personalInfoRequest);
	PersonalInfoResponse getPersonalInfoById(String infoId);
	void deleteById(String id);
	void deleteAll();
}
