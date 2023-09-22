package org.ereach.inc.services;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class InMemoryDatabase {
	private static final Set<Hospital> hospitals = new HashSet<>();
	private static final Set<HospitalAdmin> admins = new HashSet<>();
	
	public Hospital saveHospitalTemporarily(Hospital hospital){
		hospital.setId(generateTemporaryId());
		hospitals.add(hospital);
		System.out.println(hospital);
		return hospital;
	}
	
	private String generateTemporaryId() {
		String id =  UUID.randomUUID().toString();
			if (hospitals.stream().noneMatch(hospital -> Objects.equals(hospital.getId(), id)))
				return id;
			else return generateTemporaryId();
	}
	
	public Hospital getTemporarilySavedHospital(String hospitalId){
		return hospitals.stream()
				        .filter(hospital -> Objects.equals(hospital.getId(), hospitalId))
				        .findFirst()
				        .orElse(null);
	}
}
