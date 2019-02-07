package com.test.repository;

import com.test.domain.CritereExamen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CritereExamen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CritereExamenRepository extends JpaRepository<CritereExamen, Long> {

}
