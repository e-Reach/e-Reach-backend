package org.ereach.inc.services.users;

import org.ereach.inc.data.dtos.request.CreateDoctorRequest;
import org.ereach.inc.data.dtos.response.CreateDoctorResponse;
import org.ereach.inc.exceptions.EReachBaseException;

public interface DoctorService {
    CreateDoctorResponse registerNewDoctor (CreateDoctorRequest registerDoctorRequest)throws EReachBaseException;
    void removeDoctorByEmailOrPractitionerIdentificationNumber(String email, String practitionerIdentificationNumber);
}
