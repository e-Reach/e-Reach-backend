package org.ereach.inc.data.repositories.users;

import org.ereach.inc.data.models.users.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EReachPatientsRepository extends JpaRepository<Patient, String> {

    Optional<Patient> findByPatientIdentificationNumber(String s);
}
