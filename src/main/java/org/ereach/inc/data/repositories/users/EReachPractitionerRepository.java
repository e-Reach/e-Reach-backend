package org.ereach.inc.data.repositories.users;

import org.ereach.inc.data.models.users.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EReachPractitionerRepository extends JpaRepository<Practitioner, String> {
     boolean existsByEmail(String email);
	
	Optional<Practitioner> findByEmail(String practitionerEmail);
}
