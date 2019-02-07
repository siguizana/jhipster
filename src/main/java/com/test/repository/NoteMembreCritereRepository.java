package com.test.repository;

import com.test.domain.NoteMembreCritere;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NoteMembreCritere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NoteMembreCritereRepository extends JpaRepository<NoteMembreCritere, Long> {

}
