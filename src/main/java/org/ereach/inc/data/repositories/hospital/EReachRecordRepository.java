package org.ereach.inc.data.repositories.hospital;

import org.ereach.inc.data.models.entries.MedicalRecord;
import org.ereach.inc.data.models.hospital.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EReachRecordRepository extends JpaRepository<Record, String> {
<<<<<<< HEAD
    Record findByPatientIdentificationNumber(String patientIdentificationNumber);
=======
	Optional<Record> findByPatientIdentificationNumber(String patientIdentificationNumber);
>>>>>>> 9dfab38415ecb2a9bd8323675d81a4035c86bffd
}
