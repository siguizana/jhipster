package com.test.repository;

import com.test.domain.TypeCritere;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeCritere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCritereRepository extends JpaRepository<TypeCritere, Long> {

}
