package com.test.service;

import com.test.domain.DeroulementScolarite;
import com.test.repository.DeroulementScolariteRepository;
import com.test.service.dto.DeroulementScolariteDTO;
import com.test.service.mapper.DeroulementScolariteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DeroulementScolarite.
 */
@Service
@Transactional
public class DeroulementScolariteService {

    private final Logger log = LoggerFactory.getLogger(DeroulementScolariteService.class);

    private final DeroulementScolariteRepository deroulementScolariteRepository;

    private final DeroulementScolariteMapper deroulementScolariteMapper;

    public DeroulementScolariteService(DeroulementScolariteRepository deroulementScolariteRepository, DeroulementScolariteMapper deroulementScolariteMapper) {
        this.deroulementScolariteRepository = deroulementScolariteRepository;
        this.deroulementScolariteMapper = deroulementScolariteMapper;
    }

    /**
     * Save a deroulementScolarite.
     *
     * @param deroulementScolariteDTO the entity to save
     * @return the persisted entity
     */
    public DeroulementScolariteDTO save(DeroulementScolariteDTO deroulementScolariteDTO) {
        log.debug("Request to save DeroulementScolarite : {}", deroulementScolariteDTO);
        DeroulementScolarite deroulementScolarite = deroulementScolariteMapper.toEntity(deroulementScolariteDTO);
        deroulementScolarite = deroulementScolariteRepository.save(deroulementScolarite);
        return deroulementScolariteMapper.toDto(deroulementScolarite);
    }

    /**
     * Get all the deroulementScolarites.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DeroulementScolariteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeroulementScolarites");
        return deroulementScolariteRepository.findAll(pageable)
            .map(deroulementScolariteMapper::toDto);
    }


    /**
     * Get one deroulementScolarite by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<DeroulementScolariteDTO> findOne(Long id) {
        log.debug("Request to get DeroulementScolarite : {}", id);
        return deroulementScolariteRepository.findById(id)
            .map(deroulementScolariteMapper::toDto);
    }

    /**
     * Delete the deroulementScolarite by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DeroulementScolarite : {}", id);        deroulementScolariteRepository.deleteById(id);
    }
}
