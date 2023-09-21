package org.ereach.inc.data.repositories.hospital;

import org.ereach.inc.data.models.entries.MedicalRecord;
import org.ereach.inc.data.models.hospital.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachRecordRepository extends JpaRepository<Record, String> {
    Record findByPatientIdentificationNumber(String patientIdentificationNumber);
}
