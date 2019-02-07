package com.test.repository;

import com.test.domain.EtapeExamen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EtapeExamen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EtapeExamenRepository extends JpaRepository<EtapeExamen, Long> {

}
