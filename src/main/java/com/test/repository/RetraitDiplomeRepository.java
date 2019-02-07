package com.test.repository;

import com.test.domain.RetraitDiplome;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RetraitDiplome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RetraitDiplomeRepository extends JpaRepository<RetraitDiplome, Long> {

}
