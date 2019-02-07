package com.test.service;

import com.test.domain.Filiere;
import com.test.repository.FiliereRepository;
import com.test.service.dto.FiliereDTO;
import com.test.service.mapper.FiliereMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Filiere.
 */
@Service
@Transactional
public class FiliereService {

    private final Logger log = LoggerFactory.getLogger(FiliereService.class);

    private final FiliereRepository filiereRepository;

    private final FiliereMapper filiereMapper;

    public FiliereService(FiliereRepository filiereRepository, FiliereMapper filiereMapper) {
        this.filiereRepository = filiereRepository;
        this.filiereMapper = filiereMapper;
    }

    /**
     * Save a filiere.
     *
     * @param filiereDTO the entity to save
     * @return the persisted entity
     */
    public FiliereDTO save(FiliereDTO filiereDTO) {
        log.debug("Request to save Filiere : {}", filiereDTO);
        Filiere filiere = filiereMapper.toEntity(filiereDTO);
        filiere = filiereRepository.save(filiere);
        return filiereMapper.toDto(filiere);
    }

    /**
     * Get all the filieres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FiliereDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Filieres");
        return filiereRepository.findAll(pageable)
            .map(filiereMapper::toDto);
    }


    /**
     * Get one filiere by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FiliereDTO> findOne(Long id) {
        log.debug("Request to get Filiere : {}", id);
        return filiereRepository.findById(id)
            .map(filiereMapper::toDto);
    }

    /**
     * Delete the filiere by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Filiere : {}", id);        filiereRepository.deleteById(id);
    }
}
