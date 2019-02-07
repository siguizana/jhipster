package com.test.repository;

import com.test.domain.CentreExamen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CentreExamen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentreExamenRepository extends JpaRepository<CentreExamen, Long> {

}
