package com.test.repository;

import com.test.domain.TypeCentreComposition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeCentreComposition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeCentreCompositionRepository extends JpaRepository<TypeCentreComposition, Long> {

}
