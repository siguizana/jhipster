package com.test.service;

import com.test.domain.Enseigne;
import com.test.repository.EnseigneRepository;
import com.test.service.dto.EnseigneDTO;
import com.test.service.mapper.EnseigneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Enseigne.
 */
@Service
@Transactional
public class EnseigneService {

    private final Logger log = LoggerFactory.getLogger(EnseigneService.class);

    private final EnseigneRepository enseigneRepository;

    private final EnseigneMapper enseigneMapper;

    public EnseigneService(EnseigneRepository enseigneRepository, EnseigneMapper enseigneMapper) {
        this.enseigneRepository = enseigneRepository;
        this.enseigneMapper = enseigneMapper;
    }

    /**
     * Save a enseigne.
     *
     * @param enseigneDTO the entity to save
     * @return the persisted entity
     */
    public EnseigneDTO save(EnseigneDTO enseigneDTO) {
        log.debug("Request to save Enseigne : {}", enseigneDTO);
        Enseigne enseigne = enseigneMapper.toEntity(enseigneDTO);
        enseigne = enseigneRepository.save(enseigne);
        return enseigneMapper.toDto(enseigne);
    }

    /**
     * Get all the enseignes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EnseigneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enseignes");
        return enseigneRepository.findAll(pageable)
            .map(enseigneMapper::toDto);
    }


    /**
     * Get one enseigne by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EnseigneDTO> findOne(Long id) {
        log.debug("Request to get Enseigne : {}", id);
        return enseigneRepository.findById(id)
            .map(enseigneMapper::toDto);
    }

    /**
     * Delete the enseigne by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Enseigne : {}", id);        enseigneRepository.deleteById(id);
    }
}
