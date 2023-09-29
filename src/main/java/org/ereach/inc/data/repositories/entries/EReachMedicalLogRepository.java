package org.ereach.inc.data.repositories.entries;

import org.ereach.inc.data.models.entries.DoctorsReport;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EReachMedicalLogRepository extends JpaRepository<MedicalLog, String> {
	List<MedicalLog> findAllByPatientIdentificationNumber(String patientIdentificationNumber);
	
	Optional<MedicalLog> findByPatientIdentificationNumberAndDateCreated(String patientIdentificationNumber, LocalDate date);
}
