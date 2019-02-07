package com.test.repository;

import com.test.domain.ConcoursRattache;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ConcoursRattache entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcoursRattacheRepository extends JpaRepository<ConcoursRattache, Long> {

}
