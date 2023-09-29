package org.ereach.inc.data.repositories.hospital;

import org.ereach.inc.data.models.hospital.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EReachMedicationRepository extends JpaRepository<Medication, String> {
    Optional<Medication> findByDrugName(String medicationName);
}
