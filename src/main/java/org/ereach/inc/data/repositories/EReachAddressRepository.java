package org.ereach.inc.data.repositories;

import org.ereach.inc.data.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachAddressRepository extends JpaRepository<Address, String> {
}
