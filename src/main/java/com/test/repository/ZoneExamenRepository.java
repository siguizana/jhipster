package com.test.repository;

import com.test.domain.ZoneExamen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ZoneExamen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZoneExamenRepository extends JpaRepository<ZoneExamen, Long> {

}
