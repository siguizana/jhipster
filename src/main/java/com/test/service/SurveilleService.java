package com.test.service;

import com.test.domain.Surveille;
import com.test.repository.SurveilleRepository;
import com.test.service.dto.SurveilleDTO;
import com.test.service.mapper.SurveilleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Surveille.
 */
@Service
@Transactional
public class SurveilleService {

    private final Logger log = LoggerFactory.getLogger(SurveilleService.class);

    private final SurveilleRepository surveilleRepository;

    private final SurveilleMapper surveilleMapper;

    public SurveilleService(SurveilleRepository surveilleRepository, SurveilleMapper surveilleMapper) {
        this.surveilleRepository = surveilleRepository;
        this.surveilleMapper = surveilleMapper;
    }

    /**
     * Save a surveille.
     *
     * @param surveilleDTO the entity to save
     * @return the persisted entity
     */
    public SurveilleDTO save(SurveilleDTO surveilleDTO) {
        log.debug("Request to save Surveille : {}", surveilleDTO);
        Surveille surveille = surveilleMapper.toEntity(surveilleDTO);
        surveille = surveilleRepository.save(surveille);
        return surveilleMapper.toDto(surveille);
    }

    /**
     * Get all the surveilles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SurveilleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Surveilles");
        return surveilleRepository.findAll(pageable)
            .map(surveilleMapper::toDto);
    }


    /**
     * Get one surveille by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SurveilleDTO> findOne(Long id) {
        log.debug("Request to get Surveille : {}", id);
        return surveilleRepository.findById(id)
            .map(surveilleMapper::toDto);
    }

    /**
     * Delete the surveille by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Surveille : {}", id);        surveilleRepository.deleteById(id);
    }
}
