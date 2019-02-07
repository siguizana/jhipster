package com.test.repository;

import com.test.domain.Enseigne;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Enseigne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EnseigneRepository extends JpaRepository<Enseigne, Long> {

}
