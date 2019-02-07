package com.test.repository;

import com.test.domain.Fraude;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fraude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FraudeRepository extends JpaRepository<Fraude, Long> {

}
