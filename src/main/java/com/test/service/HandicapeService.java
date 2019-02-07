package com.test.service;

import com.test.domain.Handicape;
import com.test.repository.HandicapeRepository;
import com.test.service.dto.HandicapeDTO;
import com.test.service.mapper.HandicapeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Handicape.
 */
@Service
@Transactional
public class HandicapeService {

    private final Logger log = LoggerFactory.getLogger(HandicapeService.class);

    private final HandicapeRepository handicapeRepository;

    private final HandicapeMapper handicapeMapper;

    public HandicapeService(HandicapeRepository handicapeRepository, HandicapeMapper handicapeMapper) {
        this.handicapeRepository = handicapeRepository;
        this.handicapeMapper = handicapeMapper;
    }

    /**
     * Save a handicape.
     *
     * @param handicapeDTO the entity to save
     * @return the persisted entity
     */
    public HandicapeDTO save(HandicapeDTO handicapeDTO) {
        log.debug("Request to save Handicape : {}", handicapeDTO);
        Handicape handicape = handicapeMapper.toEntity(handicapeDTO);
        handicape = handicapeRepository.save(handicape);
        return handicapeMapper.toDto(handicape);
    }

    /**
     * Get all the handicapes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<HandicapeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Handicapes");
        return handicapeRepository.findAll(pageable)
            .map(handicapeMapper::toDto);
    }


    /**
     * Get one handicape by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<HandicapeDTO> findOne(Long id) {
        log.debug("Request to get Handicape : {}", id);
        return handicapeRepository.findById(id)
            .map(handicapeMapper::toDto);
    }

    /**
     * Delete the handicape by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Handicape : {}", id);        handicapeRepository.deleteById(id);
    }
}
