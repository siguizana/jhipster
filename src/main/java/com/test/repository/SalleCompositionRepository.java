package com.test.repository;

import com.test.domain.SalleComposition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SalleComposition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalleCompositionRepository extends JpaRepository<SalleComposition, Long> {

}
