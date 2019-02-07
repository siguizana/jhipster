package com.test.service;

import com.test.domain.CentreCompositioJury;
import com.test.repository.CentreCompositioJuryRepository;
import com.test.service.dto.CentreCompositioJuryDTO;
import com.test.service.mapper.CentreCompositioJuryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CentreCompositioJury.
 */
@Service
@Transactional
public class CentreCompositioJuryService {

    private final Logger log = LoggerFactory.getLogger(CentreCompositioJuryService.class);

    private final CentreCompositioJuryRepository centreCompositioJuryRepository;

    private final CentreCompositioJuryMapper centreCompositioJuryMapper;

    public CentreCompositioJuryService(CentreCompositioJuryRepository centreCompositioJuryRepository, CentreCompositioJuryMapper centreCompositioJuryMapper) {
        this.centreCompositioJuryRepository = centreCompositioJuryRepository;
        this.centreCompositioJuryMapper = centreCompositioJuryMapper;
    }

    /**
     * Save a centreCompositioJury.
     *
     * @param centreCompositioJuryDTO the entity to save
     * @return the persisted entity
     */
    public CentreCompositioJuryDTO save(CentreCompositioJuryDTO centreCompositioJuryDTO) {
        log.debug("Request to save CentreCompositioJury : {}", centreCompositioJuryDTO);
        CentreCompositioJury centreCompositioJury = centreCompositioJuryMapper.toEntity(centreCompositioJuryDTO);
        centreCompositioJury = centreCompositioJuryRepository.save(centreCompositioJury);
        return centreCompositioJuryMapper.toDto(centreCompositioJury);
    }

    /**
     * Get all the centreCompositioJuries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CentreCompositioJuryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CentreCompositioJuries");
        return centreCompositioJuryRepository.findAll(pageable)
            .map(centreCompositioJuryMapper::toDto);
    }


    /**
     * Get one centreCompositioJury by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CentreCompositioJuryDTO> findOne(Long id) {
        log.debug("Request to get CentreCompositioJury : {}", id);
        return centreCompositioJuryRepository.findById(id)
            .map(centreCompositioJuryMapper::toDto);
    }

    /**
     * Delete the centreCompositioJury by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CentreCompositioJury : {}", id);        centreCompositioJuryRepository.deleteById(id);
    }
}
