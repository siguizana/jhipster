package com.test.repository;

import com.test.domain.TypeDiplome;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeDiplome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeDiplomeRepository extends JpaRepository<TypeDiplome, Long> {

}
