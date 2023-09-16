package org.ereach.inc.data.repositories.entries;

import org.ereach.inc.data.models.entries.DoctorsReport;
import org.ereach.inc.data.models.entries.MedicalLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachMedicalLogRepository extends JpaRepository<MedicalLog, String> {
}
