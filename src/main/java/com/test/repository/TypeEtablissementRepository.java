package com.test.repository;

import com.test.domain.TypeEtablissement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeEtablissement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeEtablissementRepository extends JpaRepository<TypeEtablissement, Long> {

}
