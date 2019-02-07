package com.test.service;

import com.test.domain.CentreComposition;
import com.test.repository.CentreCompositionRepository;
import com.test.service.dto.CentreCompositionDTO;
import com.test.service.mapper.CentreCompositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CentreComposition.
 */
@Service
@Transactional
public class CentreCompositionService {

    private final Logger log = LoggerFactory.getLogger(CentreCompositionService.class);

    private final CentreCompositionRepository centreCompositionRepository;

    private final CentreCompositionMapper centreCompositionMapper;

    public CentreCompositionService(CentreCompositionRepository centreCompositionRepository, CentreCompositionMapper centreCompositionMapper) {
        this.centreCompositionRepository = centreCompositionRepository;
        this.centreCompositionMapper = centreCompositionMapper;
    }

    /**
     * Save a centreComposition.
     *
     * @param centreCompositionDTO the entity to save
     * @return the persisted entity
     */
    public CentreCompositionDTO save(CentreCompositionDTO centreCompositionDTO) {
        log.debug("Request to save CentreComposition : {}", centreCompositionDTO);
        CentreComposition centreComposition = centreCompositionMapper.toEntity(centreCompositionDTO);
        centreComposition = centreCompositionRepository.save(centreComposition);
        return centreCompositionMapper.toDto(centreComposition);
    }

    /**
     * Get all the centreCompositions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CentreCompositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CentreCompositions");
        return centreCompositionRepository.findAll(pageable)
            .map(centreCompositionMapper::toDto);
    }


    /**
     * Get one centreComposition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CentreCompositionDTO> findOne(Long id) {
        log.debug("Request to get CentreComposition : {}", id);
        return centreCompositionRepository.findById(id)
            .map(centreCompositionMapper::toDto);
    }

    /**
     * Delete the centreComposition by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CentreComposition : {}", id);        centreCompositionRepository.deleteById(id);
    }
}
