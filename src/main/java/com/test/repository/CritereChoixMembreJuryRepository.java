package com.test.repository;

import com.test.domain.CritereChoixMembreJury;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CritereChoixMembreJury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CritereChoixMembreJuryRepository extends JpaRepository<CritereChoixMembreJury, Long> {

}
