package com.test.service;

import com.test.domain.ConcoursRattache;
import com.test.repository.ConcoursRattacheRepository;
import com.test.service.dto.ConcoursRattacheDTO;
import com.test.service.mapper.ConcoursRattacheMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ConcoursRattache.
 */
@Service
@Transactional
public class ConcoursRattacheService {

    private final Logger log = LoggerFactory.getLogger(ConcoursRattacheService.class);

    private final ConcoursRattacheRepository concoursRattacheRepository;

    private final ConcoursRattacheMapper concoursRattacheMapper;

    public ConcoursRattacheService(ConcoursRattacheRepository concoursRattacheRepository, ConcoursRattacheMapper concoursRattacheMapper) {
        this.concoursRattacheRepository = concoursRattacheRepository;
        this.concoursRattacheMapper = concoursRattacheMapper;
    }

    /**
     * Save a concoursRattache.
     *
     * @param concoursRattacheDTO the entity to save
     * @return the persisted entity
     */
    public ConcoursRattacheDTO save(ConcoursRattacheDTO concoursRattacheDTO) {
        log.debug("Request to save ConcoursRattache : {}", concoursRattacheDTO);
        ConcoursRattache concoursRattache = concoursRattacheMapper.toEntity(concoursRattacheDTO);
        concoursRattache = concoursRattacheRepository.save(concoursRattache);
        return concoursRattacheMapper.toDto(concoursRattache);
    }

    /**
     * Get all the concoursRattaches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ConcoursRattacheDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConcoursRattaches");
        return concoursRattacheRepository.findAll(pageable)
            .map(concoursRattacheMapper::toDto);
    }


    /**
     * Get one concoursRattache by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ConcoursRattacheDTO> findOne(Long id) {
        log.debug("Request to get ConcoursRattache : {}", id);
        return concoursRattacheRepository.findById(id)
            .map(concoursRattacheMapper::toDto);
    }

    /**
     * Delete the concoursRattache by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConcoursRattache : {}", id);        concoursRattacheRepository.deleteById(id);
    }
}
