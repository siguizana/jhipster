package com.test.repository;

import com.test.domain.PieceAConviction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PieceAConviction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PieceAConvictionRepository extends JpaRepository<PieceAConviction, Long> {

}
