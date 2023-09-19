package org.ereach.inc.data.repositories.users;

import org.ereach.inc.data.models.users.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EReachDoctorsRepository extends JpaRepository<Doctor, String> {
    Optional<Doctor> findDoctorByDoctorIdentificationNumber(String doctorIdentificationNumber);
}
