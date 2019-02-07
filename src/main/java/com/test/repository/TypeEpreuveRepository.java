package com.test.repository;

import com.test.domain.TypeEpreuve;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeEpreuve entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeEpreuveRepository extends JpaRepository<TypeEpreuve, Long> {

}
