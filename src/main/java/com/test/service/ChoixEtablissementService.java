package com.test.service;

import com.test.domain.ChoixEtablissement;
import com.test.repository.ChoixEtablissementRepository;
import com.test.service.dto.ChoixEtablissementDTO;
import com.test.service.mapper.ChoixEtablissementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ChoixEtablissement.
 */
@Service
@Transactional
public class ChoixEtablissementService {

    private final Logger log = LoggerFactory.getLogger(ChoixEtablissementService.class);

    private final ChoixEtablissementRepository choixEtablissementRepository;

    private final ChoixEtablissementMapper choixEtablissementMapper;

    public ChoixEtablissementService(ChoixEtablissementRepository choixEtablissementRepository, ChoixEtablissementMapper choixEtablissementMapper) {
        this.choixEtablissementRepository = choixEtablissementRepository;
        this.choixEtablissementMapper = choixEtablissementMapper;
    }

    /**
     * Save a choixEtablissement.
     *
     * @param choixEtablissementDTO the entity to save
     * @return the persisted entity
     */
    public ChoixEtablissementDTO save(ChoixEtablissementDTO choixEtablissementDTO) {
        log.debug("Request to save ChoixEtablissement : {}", choixEtablissementDTO);
        ChoixEtablissement choixEtablissement = choixEtablissementMapper.toEntity(choixEtablissementDTO);
        choixEtablissement = choixEtablissementRepository.save(choixEtablissement);
        return choixEtablissementMapper.toDto(choixEtablissement);
    }

    /**
     * Get all the choixEtablissements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ChoixEtablissementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ChoixEtablissements");
        return choixEtablissementRepository.findAll(pageable)
            .map(choixEtablissementMapper::toDto);
    }


    /**
     * Get one choixEtablissement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ChoixEtablissementDTO> findOne(Long id) {
        log.debug("Request to get ChoixEtablissement : {}", id);
        return choixEtablissementRepository.findById(id)
            .map(choixEtablissementMapper::toDto);
    }

    /**
     * Delete the choixEtablissement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ChoixEtablissement : {}", id);        choixEtablissementRepository.deleteById(id);
    }
}
