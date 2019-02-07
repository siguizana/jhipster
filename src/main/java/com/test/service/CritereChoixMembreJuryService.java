package com.test.service;

import com.test.domain.CritereChoixMembreJury;
import com.test.repository.CritereChoixMembreJuryRepository;
import com.test.service.dto.CritereChoixMembreJuryDTO;
import com.test.service.mapper.CritereChoixMembreJuryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing CritereChoixMembreJury.
 */
@Service
@Transactional
public class CritereChoixMembreJuryService {

    private final Logger log = LoggerFactory.getLogger(CritereChoixMembreJuryService.class);

    private final CritereChoixMembreJuryRepository critereChoixMembreJuryRepository;

    private final CritereChoixMembreJuryMapper critereChoixMembreJuryMapper;

    public CritereChoixMembreJuryService(CritereChoixMembreJuryRepository critereChoixMembreJuryRepository, CritereChoixMembreJuryMapper critereChoixMembreJuryMapper) {
        this.critereChoixMembreJuryRepository = critereChoixMembreJuryRepository;
        this.critereChoixMembreJuryMapper = critereChoixMembreJuryMapper;
    }

    /**
     * Save a critereChoixMembreJury.
     *
     * @param critereChoixMembreJuryDTO the entity to save
     * @return the persisted entity
     */
    public CritereChoixMembreJuryDTO save(CritereChoixMembreJuryDTO critereChoixMembreJuryDTO) {
        log.debug("Request to save CritereChoixMembreJury : {}", critereChoixMembreJuryDTO);
        CritereChoixMembreJury critereChoixMembreJury = critereChoixMembreJuryMapper.toEntity(critereChoixMembreJuryDTO);
        critereChoixMembreJury = critereChoixMembreJuryRepository.save(critereChoixMembreJury);
        return critereChoixMembreJuryMapper.toDto(critereChoixMembreJury);
    }

    /**
     * Get all the critereChoixMembreJuries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CritereChoixMembreJuryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CritereChoixMembreJuries");
        return critereChoixMembreJuryRepository.findAll(pageable)
            .map(critereChoixMembreJuryMapper::toDto);
    }


    /**
     * Get one critereChoixMembreJury by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CritereChoixMembreJuryDTO> findOne(Long id) {
        log.debug("Request to get CritereChoixMembreJury : {}", id);
        return critereChoixMembreJuryRepository.findById(id)
            .map(critereChoixMembreJuryMapper::toDto);
    }

    /**
     * Delete the critereChoixMembreJury by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CritereChoixMembreJury : {}", id);        critereChoixMembreJuryRepository.deleteById(id);
    }
}
