package com.test.service;

import com.test.domain.Reclamation;
import com.test.repository.ReclamationRepository;
import com.test.service.dto.ReclamationDTO;
import com.test.service.mapper.ReclamationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Reclamation.
 */
@Service
@Transactional
public class ReclamationService {

    private final Logger log = LoggerFactory.getLogger(ReclamationService.class);

    private final ReclamationRepository reclamationRepository;

    private final ReclamationMapper reclamationMapper;

    public ReclamationService(ReclamationRepository reclamationRepository, ReclamationMapper reclamationMapper) {
        this.reclamationRepository = reclamationRepository;
        this.reclamationMapper = reclamationMapper;
    }

    /**
     * Save a reclamation.
     *
     * @param reclamationDTO the entity to save
     * @return the persisted entity
     */
    public ReclamationDTO save(ReclamationDTO reclamationDTO) {
        log.debug("Request to save Reclamation : {}", reclamationDTO);
        Reclamation reclamation = reclamationMapper.toEntity(reclamationDTO);
        reclamation = reclamationRepository.save(reclamation);
        return reclamationMapper.toDto(reclamation);
    }

    /**
     * Get all the reclamations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ReclamationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Reclamations");
        return reclamationRepository.findAll(pageable)
            .map(reclamationMapper::toDto);
    }


    /**
     * Get one reclamation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ReclamationDTO> findOne(Long id) {
        log.debug("Request to get Reclamation : {}", id);
        return reclamationRepository.findById(id)
            .map(reclamationMapper::toDto);
    }

    /**
     * Delete the reclamation by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reclamation : {}", id);        reclamationRepository.deleteById(id);
    }
}
