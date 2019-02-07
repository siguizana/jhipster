package com.test.repository;

import com.test.domain.TypeMembreJury;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the TypeMembreJury entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeMembreJuryRepository extends JpaRepository<TypeMembreJury, Long> {

    @Query(value = "select distinct type_membre_jury from TypeMembreJury type_membre_jury left join fetch type_membre_jury.critereChoixMembreJuries",
        countQuery = "select count(distinct type_membre_jury) from TypeMembreJury type_membre_jury")
    Page<TypeMembreJury> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct type_membre_jury from TypeMembreJury type_membre_jury left join fetch type_membre_jury.critereChoixMembreJuries")
    List<TypeMembreJury> findAllWithEagerRelationships();

    @Query("select type_membre_jury from TypeMembreJury type_membre_jury left join fetch type_membre_jury.critereChoixMembreJuries where type_membre_jury.id =:id")
    Optional<TypeMembreJury> findOneWithEagerRelationships(@Param("id") Long id);

}
