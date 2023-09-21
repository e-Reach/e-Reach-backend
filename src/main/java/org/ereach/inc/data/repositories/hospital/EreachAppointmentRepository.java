package org.ereach.inc.data.repositories.hospital;

import org.ereach.inc.data.models.hospital.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EreachAppointmentRepository extends JpaRepository<Appointments, String> {
}
