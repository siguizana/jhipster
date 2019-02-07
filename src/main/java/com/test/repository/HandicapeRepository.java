package com.test.repository;

import com.test.domain.Handicape;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Handicape entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HandicapeRepository extends JpaRepository<Handicape, Long> {

}
