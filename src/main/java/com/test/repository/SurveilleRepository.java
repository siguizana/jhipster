package com.test.repository;

import com.test.domain.Surveille;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Surveille entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SurveilleRepository extends JpaRepository<Surveille, Long> {

}
