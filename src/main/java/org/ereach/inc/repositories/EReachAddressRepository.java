package org.ereach.inc.repositories;

import org.ereach.inc.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachAddressRepository extends JpaRepository<Address, String> {
}
