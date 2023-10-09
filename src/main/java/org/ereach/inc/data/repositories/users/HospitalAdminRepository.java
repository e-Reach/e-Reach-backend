package org.ereach.inc.data.repositories.users;

import org.ereach.inc.data.models.users.HospitalAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HospitalAdminRepository extends JpaRepository<HospitalAdmin, String> {
	
	Optional<HospitalAdmin> findByAdminEmail(String email);
	
	void deleteByAdminEmail(String adminEmail);
}
