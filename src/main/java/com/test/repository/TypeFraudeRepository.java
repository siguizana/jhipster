package com.test.repository;

import com.test.domain.TypeFraude;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TypeFraude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeFraudeRepository extends JpaRepository<TypeFraude, Long> {

}
