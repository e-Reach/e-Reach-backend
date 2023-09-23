package org.ereach.inc.services;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

import static java.math.BigInteger.ZERO;

@Service
@AllArgsConstructor
public class InMemoryDatabase {
	private static final Set<Hospital> hospitals = new HashSet<>();
	private static final Set<HospitalAdmin> admins = new HashSet<>();
	
	public Hospital saveHospitalTemporarily(Hospital hospital){
		hospital.setId(generateTemporaryId());
		if(hospitals.stream().anyMatch(hospital1 -> Objects.equals(hospital1.getHospitalEmail(), hospital.getHospitalEmail()))){
			throw new EReachUncheckedBaseException("Invalid Email: user already exists");
		}
		hospitals.add(hospital);
		System.out.println(hospital);
		return hospital;
	}
	
	public HospitalAdmin saveHospitalAdminTemporarily(HospitalAdmin hospitalAdmin){
		hospitalAdmin.setId(generateTemporaryId());
		if(admins.stream().anyMatch(admin -> Objects.equals(admin.getAdminEmail(), hospitalAdmin.getAdminEmail()))){
			throw new EReachUncheckedBaseException("Invalid Email: user already exists");
		}
		admins.add(hospitalAdmin);
		System.out.println(hospitalAdmin);
		return hospitalAdmin;
	}
	
	public Hospital getTemporarilySavedHospital(String email){
		return hospitals.stream()
				        .filter(hospital -> Objects.equals(hospital.getHospitalEmail(), email) || Objects.equals(hospital.getId(), email))
				        .findFirst()
				        .orElse(null);
	}
	
	public HospitalAdmin getTemporarilySavedHospitalAdmin(String email){
		return admins.stream()
				        .filter(admin -> Objects.equals(admin.getAdminEmail(), email))
				        .findFirst()
				        .orElse(null);
	}
	
	private String generateTemporaryId() {
		String id =  UUID.randomUUID().toString();
		if (hospitals.stream().noneMatch(hospital -> Objects.equals(hospital.getId(), id)))
			return id;
		else return generateTemporaryId();
	}
	
	public Hospital findSavedAndActivatedHospitalByAdminEmail(String adminEmail) {
		Optional<Hospital> foundHospital = hospitals.stream()
				                                    .filter(hospital -> hospital.getAdmins()
						                                   .stream()
						                                   .anyMatch(admin -> Objects.equals(admin.getAdminEmail(), adminEmail)))
				                                    .findFirst();
		return foundHospital.orElse(null);
		
	}
}
