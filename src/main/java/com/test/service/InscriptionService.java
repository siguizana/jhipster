package com.test.service;

import com.test.domain.Inscription;
import com.test.repository.InscriptionRepository;
import com.test.service.dto.InscriptionDTO;
import com.test.service.mapper.InscriptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Inscription.
 */
@Service
@Transactional
public class InscriptionService {

    private final Logger log = LoggerFactory.getLogger(InscriptionService.class);

    private final InscriptionRepository inscriptionRepository;

    private final InscriptionMapper inscriptionMapper;

    public InscriptionService(InscriptionRepository inscriptionRepository, InscriptionMapper inscriptionMapper) {
        this.inscriptionRepository = inscriptionRepository;
        this.inscriptionMapper = inscriptionMapper;
    }

    /**
     * Save a inscription.
     *
     * @param inscriptionDTO the entity to save
     * @return the persisted entity
     */
    public InscriptionDTO save(InscriptionDTO inscriptionDTO) {
        log.debug("Request to save Inscription : {}", inscriptionDTO);
        Inscription inscription = inscriptionMapper.toEntity(inscriptionDTO);
        inscription = inscriptionRepository.save(inscription);
        return inscriptionMapper.toDto(inscription);
    }

    /**
     * Get all the inscriptions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<InscriptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Inscriptions");
        return inscriptionRepository.findAll(pageable)
            .map(inscriptionMapper::toDto);
    }


    /**
     * Get one inscription by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<InscriptionDTO> findOne(Long id) {
        log.debug("Request to get Inscription : {}", id);
        return inscriptionRepository.findById(id)
            .map(inscriptionMapper::toDto);
    }

    /**
     * Delete the inscription by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Inscription : {}", id);        inscriptionRepository.deleteById(id);
    }
}
