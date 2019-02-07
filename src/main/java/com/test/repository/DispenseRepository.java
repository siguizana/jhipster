package com.test.repository;

import com.test.domain.Dispense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Dispense entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DispenseRepository extends JpaRepository<Dispense, Long> {

    @Query(value = "select distinct dispense from Dispense dispense left join fetch dispense.epreuveSpecialiteOptions",
        countQuery = "select count(distinct dispense) from Dispense dispense")
    Page<Dispense> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct dispense from Dispense dispense left join fetch dispense.epreuveSpecialiteOptions")
    List<Dispense> findAllWithEagerRelationships();

    @Query("select dispense from Dispense dispense left join fetch dispense.epreuveSpecialiteOptions where dispense.id =:id")
    Optional<Dispense> findOneWithEagerRelationships(@Param("id") Long id);

}
