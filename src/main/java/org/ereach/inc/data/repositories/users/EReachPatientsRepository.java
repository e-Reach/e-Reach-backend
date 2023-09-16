package org.ereach.inc.data.repositories.users;

import org.ereach.inc.data.models.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachPatientsRepository extends JpaRepository<Patient, String> {
}
