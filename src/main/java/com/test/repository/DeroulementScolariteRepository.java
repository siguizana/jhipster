package com.test.repository;

import com.test.domain.DeroulementScolarite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeroulementScolarite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeroulementScolariteRepository extends JpaRepository<DeroulementScolarite, Long> {

}
