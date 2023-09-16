package org.ereach.inc.repositories.entries;

import org.ereach.inc.data.models.entries.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EReachEntryRepository extends JpaRepository<Entry,String> {
}
