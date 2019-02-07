package com.test.repository;

import com.test.domain.MembreJuryJury;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the MembreJuryJury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MembreJuryJuryRepository extends JpaRepository<MembreJuryJury, Long> {

    @Query(value = "select distinct membre_jury_jury from MembreJuryJury membre_jury_jury left join fetch membre_jury_jury.commissions",
        countQuery = "select count(distinct membre_jury_jury) from MembreJuryJury membre_jury_jury")
    Page<MembreJuryJury> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct membre_jury_jury from MembreJuryJury membre_jury_jury left join fetch membre_jury_jury.commissions")
    List<MembreJuryJury> findAllWithEagerRelationships();

    @Query("select membre_jury_jury from MembreJuryJury membre_jury_jury left join fetch membre_jury_jury.commissions where membre_jury_jury.id =:id")
    Optional<MembreJuryJury> findOneWithEagerRelationships(@Param("id") Long id);

}
