package com.test.repository;

import com.test.domain.Epreuve;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Epreuve entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpreuveRepository extends JpaRepository<Epreuve, Long> {

}
