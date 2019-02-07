package com.test.repository;

import com.test.domain.TypeDecision;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeDecision entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeDecisionRepository extends JpaRepository<TypeDecision, Long> {

}
