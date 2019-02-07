package com.test.service;

import com.test.domain.OptionConcoursRattache;
import com.test.repository.OptionConcoursRattacheRepository;
import com.test.service.dto.OptionConcoursRattacheDTO;
import com.test.service.mapper.OptionConcoursRattacheMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing OptionConcoursRattache.
 */
@Service
@Transactional
public class OptionConcoursRattacheService {

    private final Logger log = LoggerFactory.getLogger(OptionConcoursRattacheService.class);

    private final OptionConcoursRattacheRepository optionConcoursRattacheRepository;

    private final OptionConcoursRattacheMapper optionConcoursRattacheMapper;

    public OptionConcoursRattacheService(OptionConcoursRattacheRepository optionConcoursRattacheRepository, OptionConcoursRattacheMapper optionConcoursRattacheMapper) {
        this.optionConcoursRattacheRepository = optionConcoursRattacheRepository;
        this.optionConcoursRattacheMapper = optionConcoursRattacheMapper;
    }

    /**
     * Save a optionConcoursRattache.
     *
     * @param optionConcoursRattacheDTO the entity to save
     * @return the persisted entity
     */
    public OptionConcoursRattacheDTO save(OptionConcoursRattacheDTO optionConcoursRattacheDTO) {
        log.debug("Request to save OptionConcoursRattache : {}", optionConcoursRattacheDTO);
        OptionConcoursRattache optionConcoursRattache = optionConcoursRattacheMapper.toEntity(optionConcoursRattacheDTO);
        optionConcoursRattache = optionConcoursRattacheRepository.save(optionConcoursRattache);
        return optionConcoursRattacheMapper.toDto(optionConcoursRattache);
    }

    /**
     * Get all the optionConcoursRattaches.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OptionConcoursRattacheDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OptionConcoursRattaches");
        return optionConcoursRattacheRepository.findAll(pageable)
            .map(optionConcoursRattacheMapper::toDto);
    }


    /**
     * Get one optionConcoursRattache by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OptionConcoursRattacheDTO> findOne(Long id) {
        log.debug("Request to get OptionConcoursRattache : {}", id);
        return optionConcoursRattacheRepository.findById(id)
            .map(optionConcoursRattacheMapper::toDto);
    }

    /**
     * Delete the optionConcoursRattache by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OptionConcoursRattache : {}", id);        optionConcoursRattacheRepository.deleteById(id);
    }
}
