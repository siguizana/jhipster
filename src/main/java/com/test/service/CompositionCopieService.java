package com.test.service;

import com.test.domain.CompositionCopie;
import com.test.repository.CompositionCopieRepository;
import com.test.service.dto.CompositionCopieDTO;
import com.test.service.mapper.CompositionCopieMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CompositionCopie.
 */
@Service
@Transactional
public class CompositionCopieService {

    private final Logger log = LoggerFactory.getLogger(CompositionCopieService.class);

    private final CompositionCopieRepository compositionCopieRepository;

    private final CompositionCopieMapper compositionCopieMapper;

    public CompositionCopieService(CompositionCopieRepository compositionCopieRepository, CompositionCopieMapper compositionCopieMapper) {
        this.compositionCopieRepository = compositionCopieRepository;
        this.compositionCopieMapper = compositionCopieMapper;
    }

    /**
     * Save a compositionCopie.
     *
     * @param compositionCopieDTO the entity to save
     * @return the persisted entity
     */
    public CompositionCopieDTO save(CompositionCopieDTO compositionCopieDTO) {
        log.debug("Request to save CompositionCopie : {}", compositionCopieDTO);
        CompositionCopie compositionCopie = compositionCopieMapper.toEntity(compositionCopieDTO);
        compositionCopie = compositionCopieRepository.save(compositionCopie);
        return compositionCopieMapper.toDto(compositionCopie);
    }

    /**
     * Get all the compositionCopies.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CompositionCopieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompositionCopies");
        return compositionCopieRepository.findAll(pageable)
            .map(compositionCopieMapper::toDto);
    }


    /**
     * Get one compositionCopie by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CompositionCopieDTO> findOne(Long id) {
        log.debug("Request to get CompositionCopie : {}", id);
        return compositionCopieRepository.findById(id)
            .map(compositionCopieMapper::toDto);
    }

    /**
     * Delete the compositionCopie by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CompositionCopie : {}", id);        compositionCopieRepository.deleteById(id);
    }
}
