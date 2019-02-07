package com.test.repository;

import com.test.domain.ChoixEtablissement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ChoixEtablissement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChoixEtablissementRepository extends JpaRepository<ChoixEtablissement, Long> {

}
