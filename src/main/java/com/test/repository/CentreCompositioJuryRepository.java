package com.test.repository;

import com.test.domain.CentreCompositioJury;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CentreCompositioJury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentreCompositioJuryRepository extends JpaRepository<CentreCompositioJury, Long> {

}
