package org.ereach.inc.data.repositories.hospital;

import org.ereach.inc.data.models.hospital.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EReachRecordRepository extends JpaRepository<Record, String> {
	Optional<Record> findByPatientIdentificationNumber(String patientIdentificationNumber);
}
