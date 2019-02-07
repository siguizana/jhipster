package com.test.service;

import com.test.domain.Enseignant;
import com.test.repository.EnseignantRepository;
import com.test.service.dto.EnseignantDTO;
import com.test.service.mapper.EnseignantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Enseignant.
 */
@Service
@Transactional
public class EnseignantService {

    private final Logger log = LoggerFactory.getLogger(EnseignantService.class);

    private final EnseignantRepository enseignantRepository;

    private final EnseignantMapper enseignantMapper;

    public EnseignantService(EnseignantRepository enseignantRepository, EnseignantMapper enseignantMapper) {
        this.enseignantRepository = enseignantRepository;
        this.enseignantMapper = enseignantMapper;
    }

    /**
     * Save a enseignant.
     *
     * @param enseignantDTO the entity to save
     * @return the persisted entity
     */
    public EnseignantDTO save(EnseignantDTO enseignantDTO) {
        log.debug("Request to save Enseignant : {}", enseignantDTO);
        Enseignant enseignant = enseignantMapper.toEntity(enseignantDTO);
        enseignant = enseignantRepository.save(enseignant);
        return enseignantMapper.toDto(enseignant);
    }

    /**
     * Get all the enseignants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EnseignantDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enseignants");
        return enseignantRepository.findAll(pageable)
            .map(enseignantMapper::toDto);
    }


    /**
     * Get one enseignant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EnseignantDTO> findOne(Long id) {
        log.debug("Request to get Enseignant : {}", id);
        return enseignantRepository.findById(id)
            .map(enseignantMapper::toDto);
    }

    /**
     * Delete the enseignant by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Enseignant : {}", id);        enseignantRepository.deleteById(id);
    }
}
