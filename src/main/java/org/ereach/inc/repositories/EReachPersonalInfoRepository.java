package org.ereach.inc.repositories;

import org.ereach.inc.data.models.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachPersonalInfoRepository extends JpaRepository<PersonalInfo, String> {
}
