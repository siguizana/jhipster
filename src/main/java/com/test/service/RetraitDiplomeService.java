package com.test.service;

import com.test.domain.RetraitDiplome;
import com.test.repository.RetraitDiplomeRepository;
import com.test.service.dto.RetraitDiplomeDTO;
import com.test.service.mapper.RetraitDiplomeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RetraitDiplome.
 */
@Service
@Transactional
public class RetraitDiplomeService {

    private final Logger log = LoggerFactory.getLogger(RetraitDiplomeService.class);

    private final RetraitDiplomeRepository retraitDiplomeRepository;

    private final RetraitDiplomeMapper retraitDiplomeMapper;

    public RetraitDiplomeService(RetraitDiplomeRepository retraitDiplomeRepository, RetraitDiplomeMapper retraitDiplomeMapper) {
        this.retraitDiplomeRepository = retraitDiplomeRepository;
        this.retraitDiplomeMapper = retraitDiplomeMapper;
    }

    /**
     * Save a retraitDiplome.
     *
     * @param retraitDiplomeDTO the entity to save
     * @return the persisted entity
     */
    public RetraitDiplomeDTO save(RetraitDiplomeDTO retraitDiplomeDTO) {
        log.debug("Request to save RetraitDiplome : {}", retraitDiplomeDTO);
        RetraitDiplome retraitDiplome = retraitDiplomeMapper.toEntity(retraitDiplomeDTO);
        retraitDiplome = retraitDiplomeRepository.save(retraitDiplome);
        return retraitDiplomeMapper.toDto(retraitDiplome);
    }

    /**
     * Get all the retraitDiplomes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RetraitDiplomeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RetraitDiplomes");
        return retraitDiplomeRepository.findAll(pageable)
            .map(retraitDiplomeMapper::toDto);
    }


    /**
     * Get one retraitDiplome by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RetraitDiplomeDTO> findOne(Long id) {
        log.debug("Request to get RetraitDiplome : {}", id);
        return retraitDiplomeRepository.findById(id)
            .map(retraitDiplomeMapper::toDto);
    }

    /**
     * Delete the retraitDiplome by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RetraitDiplome : {}", id);        retraitDiplomeRepository.deleteById(id);
    }
}
