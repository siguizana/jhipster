package com.test.repository;

import com.test.domain.CompositionCopie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CompositionCopie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompositionCopieRepository extends JpaRepository<CompositionCopie, Long> {

}
