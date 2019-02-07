package com.test.service;

import com.test.domain.Fraude;
import com.test.repository.FraudeRepository;
import com.test.service.dto.FraudeDTO;
import com.test.service.mapper.FraudeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Fraude.
 */
@Service
@Transactional
public class FraudeService {

    private final Logger log = LoggerFactory.getLogger(FraudeService.class);

    private final FraudeRepository fraudeRepository;

    private final FraudeMapper fraudeMapper;

    public FraudeService(FraudeRepository fraudeRepository, FraudeMapper fraudeMapper) {
        this.fraudeRepository = fraudeRepository;
        this.fraudeMapper = fraudeMapper;
    }

    /**
     * Save a fraude.
     *
     * @param fraudeDTO the entity to save
     * @return the persisted entity
     */
    public FraudeDTO save(FraudeDTO fraudeDTO) {
        log.debug("Request to save Fraude : {}", fraudeDTO);
        Fraude fraude = fraudeMapper.toEntity(fraudeDTO);
        fraude = fraudeRepository.save(fraude);
        return fraudeMapper.toDto(fraude);
    }

    /**
     * Get all the fraudes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<FraudeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Fraudes");
        return fraudeRepository.findAll(pageable)
            .map(fraudeMapper::toDto);
    }


    /**
     * Get one fraude by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FraudeDTO> findOne(Long id) {
        log.debug("Request to get Fraude : {}", id);
        return fraudeRepository.findById(id)
            .map(fraudeMapper::toDto);
    }

    /**
     * Delete the fraude by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Fraude : {}", id);        fraudeRepository.deleteById(id);
    }
}
