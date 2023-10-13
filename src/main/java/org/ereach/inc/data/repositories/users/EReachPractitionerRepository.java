package org.ereach.inc.data.repositories.users;

import org.ereach.inc.data.models.users.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.expression.spel.ast.OpAnd;

import java.util.Optional;

import java.util.Optional;

public interface EReachPractitionerRepository extends JpaRepository<Practitioner, String> {
     boolean existsByEmail(String email);
<<<<<<< HEAD

    Optional<Practitioner> findByEmailOrPractitionerIdentificationNumber(String practitionerEmail, String practitionerIdentificationNumber);
=======
	
	Optional<Practitioner> findByEmail(String practitionerEmail);
>>>>>>> 7b64573a0aed6932440053c906836aad320d8048
}
