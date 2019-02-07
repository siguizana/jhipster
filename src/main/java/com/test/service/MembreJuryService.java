package com.test.service;

import com.test.domain.MembreJury;
import com.test.repository.MembreJuryRepository;
import com.test.service.dto.MembreJuryDTO;
import com.test.service.mapper.MembreJuryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MembreJury.
 */
@Service
@Transactional
public class MembreJuryService {

    private final Logger log = LoggerFactory.getLogger(MembreJuryService.class);

    private final MembreJuryRepository membreJuryRepository;

    private final MembreJuryMapper membreJuryMapper;

    public MembreJuryService(MembreJuryRepository membreJuryRepository, MembreJuryMapper membreJuryMapper) {
        this.membreJuryRepository = membreJuryRepository;
        this.membreJuryMapper = membreJuryMapper;
    }

    /**
     * Save a membreJury.
     *
     * @param membreJuryDTO the entity to save
     * @return the persisted entity
     */
    public MembreJuryDTO save(MembreJuryDTO membreJuryDTO) {
        log.debug("Request to save MembreJury : {}", membreJuryDTO);
        MembreJury membreJury = membreJuryMapper.toEntity(membreJuryDTO);
        membreJury = membreJuryRepository.save(membreJury);
        return membreJuryMapper.toDto(membreJury);
    }

    /**
     * Get all the membreJuries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MembreJuryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MembreJuries");
        return membreJuryRepository.findAll(pageable)
            .map(membreJuryMapper::toDto);
    }

    /**
     * Get all the MembreJury with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<MembreJuryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return membreJuryRepository.findAllWithEagerRelationships(pageable).map(membreJuryMapper::toDto);
    }
    

    /**
     * Get one membreJury by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MembreJuryDTO> findOne(Long id) {
        log.debug("Request to get MembreJury : {}", id);
        return membreJuryRepository.findOneWithEagerRelationships(id)
            .map(membreJuryMapper::toDto);
    }

    /**
     * Delete the membreJury by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MembreJury : {}", id);        membreJuryRepository.deleteById(id);
    }
}
