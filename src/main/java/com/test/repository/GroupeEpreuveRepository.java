package com.test.repository;

import com.test.domain.GroupeEpreuve;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the GroupeEpreuve entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupeEpreuveRepository extends JpaRepository<GroupeEpreuve, Long> {

}
