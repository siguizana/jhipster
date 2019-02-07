package com.test.repository;

import com.test.domain.TypeExamen;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeExamen entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeExamenRepository extends JpaRepository<TypeExamen, Long> {

}
