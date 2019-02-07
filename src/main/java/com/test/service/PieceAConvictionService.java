package com.test.service;

import com.test.domain.PieceAConviction;
import com.test.repository.PieceAConvictionRepository;
import com.test.service.dto.PieceAConvictionDTO;
import com.test.service.mapper.PieceAConvictionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PieceAConviction.
 */
@Service
@Transactional
public class PieceAConvictionService {

    private final Logger log = LoggerFactory.getLogger(PieceAConvictionService.class);

    private final PieceAConvictionRepository pieceAConvictionRepository;

    private final PieceAConvictionMapper pieceAConvictionMapper;

    public PieceAConvictionService(PieceAConvictionRepository pieceAConvictionRepository, PieceAConvictionMapper pieceAConvictionMapper) {
        this.pieceAConvictionRepository = pieceAConvictionRepository;
        this.pieceAConvictionMapper = pieceAConvictionMapper;
    }

    /**
     * Save a pieceAConviction.
     *
     * @param pieceAConvictionDTO the entity to save
     * @return the persisted entity
     */
    public PieceAConvictionDTO save(PieceAConvictionDTO pieceAConvictionDTO) {
        log.debug("Request to save PieceAConviction : {}", pieceAConvictionDTO);
        PieceAConviction pieceAConviction = pieceAConvictionMapper.toEntity(pieceAConvictionDTO);
        pieceAConviction = pieceAConvictionRepository.save(pieceAConviction);
        return pieceAConvictionMapper.toDto(pieceAConviction);
    }

    /**
     * Get all the pieceAConvictions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PieceAConvictionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PieceAConvictions");
        return pieceAConvictionRepository.findAll(pageable)
            .map(pieceAConvictionMapper::toDto);
    }


    /**
     * Get one pieceAConviction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PieceAConvictionDTO> findOne(Long id) {
        log.debug("Request to get PieceAConviction : {}", id);
        return pieceAConvictionRepository.findById(id)
            .map(pieceAConvictionMapper::toDto);
    }

    /**
     * Delete the pieceAConviction by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PieceAConviction : {}", id);        pieceAConvictionRepository.deleteById(id);
    }
}
