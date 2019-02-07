package com.test.service;

import com.test.domain.CentreExamen;
import com.test.repository.CentreExamenRepository;
import com.test.service.dto.CentreExamenDTO;
import com.test.service.mapper.CentreExamenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CentreExamen.
 */
@Service
@Transactional
public class CentreExamenService {

    private final Logger log = LoggerFactory.getLogger(CentreExamenService.class);

    private final CentreExamenRepository centreExamenRepository;

    private final CentreExamenMapper centreExamenMapper;

    public CentreExamenService(CentreExamenRepository centreExamenRepository, CentreExamenMapper centreExamenMapper) {
        this.centreExamenRepository = centreExamenRepository;
        this.centreExamenMapper = centreExamenMapper;
    }

    /**
     * Save a centreExamen.
     *
     * @param centreExamenDTO the entity to save
     * @return the persisted entity
     */
    public CentreExamenDTO save(CentreExamenDTO centreExamenDTO) {
        log.debug("Request to save CentreExamen : {}", centreExamenDTO);
        CentreExamen centreExamen = centreExamenMapper.toEntity(centreExamenDTO);
        centreExamen = centreExamenRepository.save(centreExamen);
        return centreExamenMapper.toDto(centreExamen);
    }

    /**
     * Get all the centreExamen.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CentreExamenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CentreExamen");
        return centreExamenRepository.findAll(pageable)
            .map(centreExamenMapper::toDto);
    }


    /**
     * Get one centreExamen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CentreExamenDTO> findOne(Long id) {
        log.debug("Request to get CentreExamen : {}", id);
        return centreExamenRepository.findById(id)
            .map(centreExamenMapper::toDto);
    }

    /**
     * Delete the centreExamen by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CentreExamen : {}", id);        centreExamenRepository.deleteById(id);
    }
}
