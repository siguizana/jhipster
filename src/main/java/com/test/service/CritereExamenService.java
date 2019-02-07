package com.test.service;

import com.test.domain.CritereExamen;
import com.test.repository.CritereExamenRepository;
import com.test.service.dto.CritereExamenDTO;
import com.test.service.mapper.CritereExamenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CritereExamen.
 */
@Service
@Transactional
public class CritereExamenService {

    private final Logger log = LoggerFactory.getLogger(CritereExamenService.class);

    private final CritereExamenRepository critereExamenRepository;

    private final CritereExamenMapper critereExamenMapper;

    public CritereExamenService(CritereExamenRepository critereExamenRepository, CritereExamenMapper critereExamenMapper) {
        this.critereExamenRepository = critereExamenRepository;
        this.critereExamenMapper = critereExamenMapper;
    }

    /**
     * Save a critereExamen.
     *
     * @param critereExamenDTO the entity to save
     * @return the persisted entity
     */
    public CritereExamenDTO save(CritereExamenDTO critereExamenDTO) {
        log.debug("Request to save CritereExamen : {}", critereExamenDTO);
        CritereExamen critereExamen = critereExamenMapper.toEntity(critereExamenDTO);
        critereExamen = critereExamenRepository.save(critereExamen);
        return critereExamenMapper.toDto(critereExamen);
    }

    /**
     * Get all the critereExamen.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CritereExamenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CritereExamen");
        return critereExamenRepository.findAll(pageable)
            .map(critereExamenMapper::toDto);
    }


    /**
     * Get one critereExamen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CritereExamenDTO> findOne(Long id) {
        log.debug("Request to get CritereExamen : {}", id);
        return critereExamenRepository.findById(id)
            .map(critereExamenMapper::toDto);
    }

    /**
     * Delete the critereExamen by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CritereExamen : {}", id);        critereExamenRepository.deleteById(id);
    }
}
