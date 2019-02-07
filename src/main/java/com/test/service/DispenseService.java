package com.test.service;

import com.test.domain.Dispense;
import com.test.repository.DispenseRepository;
import com.test.service.dto.DispenseDTO;
import com.test.service.mapper.DispenseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Dispense.
 */
@Service
@Transactional
public class DispenseService {

    private final Logger log = LoggerFactory.getLogger(DispenseService.class);

    private final DispenseRepository dispenseRepository;

    private final DispenseMapper dispenseMapper;

    public DispenseService(DispenseRepository dispenseRepository, DispenseMapper dispenseMapper) {
        this.dispenseRepository = dispenseRepository;
        this.dispenseMapper = dispenseMapper;
    }

    /**
     * Save a dispense.
     *
     * @param dispenseDTO the entity to save
     * @return the persisted entity
     */
    public DispenseDTO save(DispenseDTO dispenseDTO) {
        log.debug("Request to save Dispense : {}", dispenseDTO);
        Dispense dispense = dispenseMapper.toEntity(dispenseDTO);
        dispense = dispenseRepository.save(dispense);
        return dispenseMapper.toDto(dispense);
    }

    /**
     * Get all the dispenses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DispenseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Dispenses");
        return dispenseRepository.findAll(pageable)
            .map(dispenseMapper::toDto);
    }

    /**
     * Get all the Dispense with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<DispenseDTO> findAllWithEagerRelationships(Pageable pageable) {
        return dispenseRepository.findAllWithEagerRelationships(pageable).map(dispenseMapper::toDto);
    }
    

    /**
     * Get one dispense by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DispenseDTO> findOne(Long id) {
        log.debug("Request to get Dispense : {}", id);
        return dispenseRepository.findOneWithEagerRelationships(id)
            .map(dispenseMapper::toDto);
    }

    /**
     * Delete the dispense by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Dispense : {}", id);        dispenseRepository.deleteById(id);
    }
}
