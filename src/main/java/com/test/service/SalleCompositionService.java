package com.test.service;

import com.test.domain.SalleComposition;
import com.test.repository.SalleCompositionRepository;
import com.test.service.dto.SalleCompositionDTO;
import com.test.service.mapper.SalleCompositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SalleComposition.
 */
@Service
@Transactional
public class SalleCompositionService {

    private final Logger log = LoggerFactory.getLogger(SalleCompositionService.class);

    private final SalleCompositionRepository salleCompositionRepository;

    private final SalleCompositionMapper salleCompositionMapper;

    public SalleCompositionService(SalleCompositionRepository salleCompositionRepository, SalleCompositionMapper salleCompositionMapper) {
        this.salleCompositionRepository = salleCompositionRepository;
        this.salleCompositionMapper = salleCompositionMapper;
    }

    /**
     * Save a salleComposition.
     *
     * @param salleCompositionDTO the entity to save
     * @return the persisted entity
     */
    public SalleCompositionDTO save(SalleCompositionDTO salleCompositionDTO) {
        log.debug("Request to save SalleComposition : {}", salleCompositionDTO);
        SalleComposition salleComposition = salleCompositionMapper.toEntity(salleCompositionDTO);
        salleComposition = salleCompositionRepository.save(salleComposition);
        return salleCompositionMapper.toDto(salleComposition);
    }

    /**
     * Get all the salleCompositions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SalleCompositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalleCompositions");
        return salleCompositionRepository.findAll(pageable)
            .map(salleCompositionMapper::toDto);
    }


    /**
     * Get one salleComposition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SalleCompositionDTO> findOne(Long id) {
        log.debug("Request to get SalleComposition : {}", id);
        return salleCompositionRepository.findById(id)
            .map(salleCompositionMapper::toDto);
    }

    /**
     * Delete the salleComposition by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalleComposition : {}", id);        salleCompositionRepository.deleteById(id);
    }
}
