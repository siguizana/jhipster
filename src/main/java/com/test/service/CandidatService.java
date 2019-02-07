package com.test.service;

import com.test.domain.Candidat;
import com.test.repository.CandidatRepository;
import com.test.service.dto.CandidatDTO;
import com.test.service.mapper.CandidatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Candidat.
 */
@Service
@Transactional
public class CandidatService {

    private final Logger log = LoggerFactory.getLogger(CandidatService.class);

    private final CandidatRepository candidatRepository;

    private final CandidatMapper candidatMapper;

    public CandidatService(CandidatRepository candidatRepository, CandidatMapper candidatMapper) {
        this.candidatRepository = candidatRepository;
        this.candidatMapper = candidatMapper;
    }

    /**
     * Save a candidat.
     *
     * @param candidatDTO the entity to save
     * @return the persisted entity
     */
    public CandidatDTO save(CandidatDTO candidatDTO) {
        log.debug("Request to save Candidat : {}", candidatDTO);
        Candidat candidat = candidatMapper.toEntity(candidatDTO);
        candidat = candidatRepository.save(candidat);
        return candidatMapper.toDto(candidat);
    }

    /**
     * Get all the candidats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CandidatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Candidats");
        return candidatRepository.findAll(pageable)
            .map(candidatMapper::toDto);
    }


    /**
     * Get one candidat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CandidatDTO> findOne(Long id) {
        log.debug("Request to get Candidat : {}", id);
        return candidatRepository.findById(id)
            .map(candidatMapper::toDto);
    }

    /**
     * Delete the candidat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Candidat : {}", id);        candidatRepository.deleteById(id);
    }
}
