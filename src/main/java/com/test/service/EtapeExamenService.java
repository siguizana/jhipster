package com.test.service;

import com.test.domain.EtapeExamen;
import com.test.repository.EtapeExamenRepository;
import com.test.service.dto.EtapeExamenDTO;
import com.test.service.mapper.EtapeExamenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing EtapeExamen.
 */
@Service
@Transactional
public class EtapeExamenService {

    private final Logger log = LoggerFactory.getLogger(EtapeExamenService.class);

    private final EtapeExamenRepository etapeExamenRepository;

    private final EtapeExamenMapper etapeExamenMapper;

    public EtapeExamenService(EtapeExamenRepository etapeExamenRepository, EtapeExamenMapper etapeExamenMapper) {
        this.etapeExamenRepository = etapeExamenRepository;
        this.etapeExamenMapper = etapeExamenMapper;
    }

    /**
     * Save a etapeExamen.
     *
     * @param etapeExamenDTO the entity to save
     * @return the persisted entity
     */
    public EtapeExamenDTO save(EtapeExamenDTO etapeExamenDTO) {
        log.debug("Request to save EtapeExamen : {}", etapeExamenDTO);
        EtapeExamen etapeExamen = etapeExamenMapper.toEntity(etapeExamenDTO);
        etapeExamen = etapeExamenRepository.save(etapeExamen);
        return etapeExamenMapper.toDto(etapeExamen);
    }

    /**
     * Get all the etapeExamen.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EtapeExamenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EtapeExamen");
        return etapeExamenRepository.findAll(pageable)
            .map(etapeExamenMapper::toDto);
    }


    /**
     * Get one etapeExamen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EtapeExamenDTO> findOne(Long id) {
        log.debug("Request to get EtapeExamen : {}", id);
        return etapeExamenRepository.findById(id)
            .map(etapeExamenMapper::toDto);
    }

    /**
     * Delete the etapeExamen by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EtapeExamen : {}", id);        etapeExamenRepository.deleteById(id);
    }
}
