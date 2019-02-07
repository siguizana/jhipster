package com.test.service;

import com.test.domain.Sanction;
import com.test.repository.SanctionRepository;
import com.test.service.dto.SanctionDTO;
import com.test.service.mapper.SanctionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Sanction.
 */
@Service
@Transactional
public class SanctionService {

    private final Logger log = LoggerFactory.getLogger(SanctionService.class);

    private final SanctionRepository sanctionRepository;

    private final SanctionMapper sanctionMapper;

    public SanctionService(SanctionRepository sanctionRepository, SanctionMapper sanctionMapper) {
        this.sanctionRepository = sanctionRepository;
        this.sanctionMapper = sanctionMapper;
    }

    /**
     * Save a sanction.
     *
     * @param sanctionDTO the entity to save
     * @return the persisted entity
     */
    public SanctionDTO save(SanctionDTO sanctionDTO) {
        log.debug("Request to save Sanction : {}", sanctionDTO);
        Sanction sanction = sanctionMapper.toEntity(sanctionDTO);
        sanction = sanctionRepository.save(sanction);
        return sanctionMapper.toDto(sanction);
    }

    /**
     * Get all the sanctions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SanctionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sanctions");
        return sanctionRepository.findAll(pageable)
            .map(sanctionMapper::toDto);
    }


    /**
     * Get one sanction by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SanctionDTO> findOne(Long id) {
        log.debug("Request to get Sanction : {}", id);
        return sanctionRepository.findById(id)
            .map(sanctionMapper::toDto);
    }

    /**
     * Delete the sanction by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Sanction : {}", id);        sanctionRepository.deleteById(id);
    }
}
