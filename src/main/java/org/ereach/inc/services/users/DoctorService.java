package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.CreatePractitionerRequest;
import org.ereach.inc.data.dtos.response.PractitionerResponse;
import org.ereach.inc.exceptions.EReachBaseException;

public interface DoctorService {
    PractitionerResponse registerNewDoctor (CreatePractitionerRequest registerDoctorRequest)throws EReachBaseException;
    PractitionerResponse activatePractitionerAccount(CreatePractitionerRequest registerDoctorRequest);
    void removePractitionerByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber);
}
