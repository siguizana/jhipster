package com.test.repository;

import com.test.domain.EpreuveSpecialiteOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EpreuveSpecialiteOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EpreuveSpecialiteOptionRepository extends JpaRepository<EpreuveSpecialiteOption, Long> {

}
