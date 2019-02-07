package com.test.service;

import com.test.domain.Epreuve;
import com.test.repository.EpreuveRepository;
import com.test.service.dto.EpreuveDTO;
import com.test.service.mapper.EpreuveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Epreuve.
 */
@Service
@Transactional
public class EpreuveService {

    private final Logger log = LoggerFactory.getLogger(EpreuveService.class);

    private final EpreuveRepository epreuveRepository;

    private final EpreuveMapper epreuveMapper;

    public EpreuveService(EpreuveRepository epreuveRepository, EpreuveMapper epreuveMapper) {
        this.epreuveRepository = epreuveRepository;
        this.epreuveMapper = epreuveMapper;
    }

    /**
     * Save a epreuve.
     *
     * @param epreuveDTO the entity to save
     * @return the persisted entity
     */
    public EpreuveDTO save(EpreuveDTO epreuveDTO) {
        log.debug("Request to save Epreuve : {}", epreuveDTO);
        Epreuve epreuve = epreuveMapper.toEntity(epreuveDTO);
        epreuve = epreuveRepository.save(epreuve);
        return epreuveMapper.toDto(epreuve);
    }

    /**
     * Get all the epreuves.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EpreuveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Epreuves");
        return epreuveRepository.findAll(pageable)
            .map(epreuveMapper::toDto);
    }


    /**
     * Get one epreuve by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EpreuveDTO> findOne(Long id) {
        log.debug("Request to get Epreuve : {}", id);
        return epreuveRepository.findById(id)
            .map(epreuveMapper::toDto);
    }

    /**
     * Delete the epreuve by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Epreuve : {}", id);        epreuveRepository.deleteById(id);
    }
}
