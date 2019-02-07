package com.test.repository;

import com.test.domain.OptionConcoursRattache;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OptionConcoursRattache entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionConcoursRattacheRepository extends JpaRepository<OptionConcoursRattache, Long> {

}
