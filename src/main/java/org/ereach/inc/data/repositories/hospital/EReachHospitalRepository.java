package org.ereach.inc.data.repositories.hospital;

import org.ereach.inc.data.models.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EReachHospitalRepository extends JpaRepository<Hospital, String> {

    Optional<Hospital> findByHospitalEmail(String email);


}
