package com.test.repository;

import com.test.domain.Sanction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Sanction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SanctionRepository extends JpaRepository<Sanction, Long> {

}
