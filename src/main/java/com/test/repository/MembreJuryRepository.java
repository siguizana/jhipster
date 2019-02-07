package com.test.repository;

import com.test.domain.MembreJury;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MembreJury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreJuryRepository extends JpaRepository<MembreJury, Long> {

    @Query(value = "select distinct membre_jury from MembreJury membre_jury left join fetch membre_jury.fraudes left join fetch membre_jury.compositionCopies",
        countQuery = "select count(distinct membre_jury) from MembreJury membre_jury")
    Page<MembreJury> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct membre_jury from MembreJury membre_jury left join fetch membre_jury.fraudes left join fetch membre_jury.compositionCopies")
    List<MembreJury> findAllWithEagerRelationships();

    @Query("select membre_jury from MembreJury membre_jury left join fetch membre_jury.fraudes left join fetch membre_jury.compositionCopies where membre_jury.id =:id")
    Optional<MembreJury> findOneWithEagerRelationships(@Param("id") Long id);

}
