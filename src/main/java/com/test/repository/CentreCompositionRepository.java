package com.test.repository;

import com.test.domain.CentreComposition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CentreComposition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentreCompositionRepository extends JpaRepository<CentreComposition, Long> {

}
