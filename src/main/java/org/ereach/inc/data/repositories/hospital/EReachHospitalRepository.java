package org.ereach.inc.data.repositories.hospital;

import org.ereach.inc.data.models.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachHospitalRepository extends JpaRepository<Hospital, String> {
}
