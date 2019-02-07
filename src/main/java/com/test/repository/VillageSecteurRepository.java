package com.test.repository;

import com.test.domain.VillageSecteur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the VillageSecteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VillageSecteurRepository extends JpaRepository<VillageSecteur, Long> {

}
