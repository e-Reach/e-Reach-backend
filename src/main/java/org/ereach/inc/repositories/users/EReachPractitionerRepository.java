package org.ereach.inc.repositories.users;

import org.ereach.inc.data.models.users.Practitioner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachPractitionerRepository extends JpaRepository<Practitioner, String> {
}
