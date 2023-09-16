package org.ereach.inc.data.repositories.hospital;

import org.ereach.inc.data.models.hospital.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachMedicationRepository extends JpaRepository<Medication, String> {
}
