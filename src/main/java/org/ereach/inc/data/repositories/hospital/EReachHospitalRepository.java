package org.ereach.inc.data.repositories.hospital;

import jakarta.transaction.Transactional;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.ereach.inc.data.models.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface EReachHospitalRepository extends JpaRepository<Hospital, String> {

    Optional<Hospital> findByHospitalEmail(String email);
	
	 @Transactional
	 void deleteByHospitalEmail(String mail);
	
	boolean existsByHospitalEmail(String mail);
}
