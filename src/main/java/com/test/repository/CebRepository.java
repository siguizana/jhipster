package com.test.repository;

import com.test.domain.Ceb;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Ceb entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CebRepository extends JpaRepository<Ceb, Long> {

}
