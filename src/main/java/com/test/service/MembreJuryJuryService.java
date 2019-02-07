package com.test.service;

import com.test.domain.MembreJuryJury;
import com.test.repository.MembreJuryJuryRepository;
import com.test.service.dto.MembreJuryJuryDTO;
import com.test.service.mapper.MembreJuryJuryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing MembreJuryJury.
 */
@Service
@Transactional
public class MembreJuryJuryService {

    private final Logger log = LoggerFactory.getLogger(MembreJuryJuryService.class);

    private final MembreJuryJuryRepository membreJuryJuryRepository;

    private final MembreJuryJuryMapper membreJuryJuryMapper;

    public MembreJuryJuryService(MembreJuryJuryRepository membreJuryJuryRepository, MembreJuryJuryMapper membreJuryJuryMapper) {
        this.membreJuryJuryRepository = membreJuryJuryRepository;
        this.membreJuryJuryMapper = membreJuryJuryMapper;
    }

    /**
     * Save a membreJuryJury.
     *
     * @param membreJuryJuryDTO the entity to save
     * @return the persisted entity
     */
    public MembreJuryJuryDTO save(MembreJuryJuryDTO membreJuryJuryDTO) {
        log.debug("Request to save MembreJuryJury : {}", membreJuryJuryDTO);
        MembreJuryJury membreJuryJury = membreJuryJuryMapper.toEntity(membreJuryJuryDTO);
        membreJuryJury = membreJuryJuryRepository.save(membreJuryJury);
        return membreJuryJuryMapper.toDto(membreJuryJury);
    }

    /**
     * Get all the membreJuryJuries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MembreJuryJuryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MembreJuryJuries");
        return membreJuryJuryRepository.findAll(pageable)
            .map(membreJuryJuryMapper::toDto);
    }

    /**
     * Get all the MembreJuryJury with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<MembreJuryJuryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return membreJuryJuryRepository.findAllWithEagerRelationships(pageable).map(membreJuryJuryMapper::toDto);
    }
    

    /**
     * Get one membreJuryJury by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MembreJuryJuryDTO> findOne(Long id) {
        log.debug("Request to get MembreJuryJury : {}", id);
        return membreJuryJuryRepository.findOneWithEagerRelationships(id)
            .map(membreJuryJuryMapper::toDto);
    }

    /**
     * Delete the membreJuryJury by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MembreJuryJury : {}", id);        membreJuryJuryRepository.deleteById(id);
    }
}
