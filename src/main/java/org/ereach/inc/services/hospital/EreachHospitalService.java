package org.ereach.inc.services.hospital;

import org.ereach.inc.data.dtos.response.HospitalResponse;

import java.util.HashMap;
import java.util.Map;

public class EreachHospitalService implements HospitalService {
    private final Map<String, HospitalResponse> registeredHospitals = new HashMap<>();

    public void registerHospital(String officerIdentificationNumber, HospitalResponse hospitalResponse) {
        registeredHospitals.put(officerIdentificationNumber, hospitalResponse);

    }

    @Override
    public HospitalResponse getHospitalRegisteredWith(String officerIdentificationNumber) {
        return registeredHospitals.getOrDefault(officerIdentificationNumber, null);
    }
}

}
