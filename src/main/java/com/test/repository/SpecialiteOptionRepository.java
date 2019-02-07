package com.test.repository;

import com.test.domain.SpecialiteOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SpecialiteOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialiteOptionRepository extends JpaRepository<SpecialiteOption, Long> {

}
