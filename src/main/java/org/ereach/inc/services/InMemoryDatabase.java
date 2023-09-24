package org.ereach.inc.services;

import lombok.AllArgsConstructor;
import org.ereach.inc.data.models.hospital.Hospital;
import org.ereach.inc.data.models.users.HospitalAdmin;
import org.ereach.inc.exceptions.EReachUncheckedBaseException;
import org.ereach.inc.exceptions.RequestInvalidException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class InMemoryDatabase {
	public static final String USER_ALREADY_EXISTS = "Invalid Email: user already exists";
	private static Set<Hospital> hospitals = new HashSet<>();
	private static Set<HospitalAdmin> admins = new HashSet<>();
	private static final Map<String, Hospital> savedHospitals = new HashMap<>();
	
	public Hospital temporarySave(Hospital hospital){
		hospital.setId(generateTemporaryId());
		if(hospitals.stream().anyMatch(hospital1 -> Objects.equals(hospital1.getHospitalEmail(), hospital.getHospitalEmail())))
			throw new EReachUncheckedBaseException(USER_ALREADY_EXISTS);hospitals.add(hospital);
		System.out.println(hospital);
		return hospital;
	}
	
	public void temporarySave(HospitalAdmin hospitalAdmin){
		hospitalAdmin.setId(generateTemporaryId());
		if(admins.stream().anyMatch(admin -> Objects.equals(admin.getAdminEmail(), hospitalAdmin.getAdminEmail())))
			throw new EReachUncheckedBaseException(USER_ALREADY_EXISTS);
		admins.add(hospitalAdmin);
		System.out.println(hospitalAdmin);
	}
	
	public void addHospitalToSavedHospitals(String adminEmail, Hospital hospital){
		savedHospitals.put(adminEmail, hospital);
	}
	
	public Hospital retrieveHospitalFromInMemory(String email){
		return hospitals.stream()
				        .filter(hospital -> Objects.equals(hospital.getHospitalEmail(), email) || Objects.equals(hospital.getId(), email))
				        .findFirst()
				        .orElse(null);
	}
	
	public HospitalAdmin retrieveAdminFromInMemory(String email){
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
	
	public Hospital findSavedAndActivatedHospitalByAdminEmail(String adminEmail) throws RequestInvalidException {
		Hospital savedHospital = savedHospitals.get(adminEmail);
		if (savedHospital == null)
			throw new RequestInvalidException("");
		return savedHospital;
	}
	
	public void deleteAll(String role){
		if (Objects.equals(role, "admin"))
			deleteAllAdmins();
		else if (Objects.equals(role, "hospital"))deleteAllHospitals();
	}
	
	private void deleteAllHospitals() {
		hospitals = new HashSet<>();
	}
	
	private void deleteAllAdmins() {
		admins = new HashSet<>();
	}
}
