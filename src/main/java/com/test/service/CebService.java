package com.test.service;

import com.test.domain.Ceb;
import com.test.repository.CebRepository;
import com.test.service.dto.CebDTO;
import com.test.service.mapper.CebMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Ceb.
 */
@Service
@Transactional
public class CebService {

    private final Logger log = LoggerFactory.getLogger(CebService.class);

    private final CebRepository cebRepository;

    private final CebMapper cebMapper;

    public CebService(CebRepository cebRepository, CebMapper cebMapper) {
        this.cebRepository = cebRepository;
        this.cebMapper = cebMapper;
    }

    /**
     * Save a ceb.
     *
     * @param cebDTO the entity to save
     * @return the persisted entity
     */
    public CebDTO save(CebDTO cebDTO) {
        log.debug("Request to save Ceb : {}", cebDTO);
        Ceb ceb = cebMapper.toEntity(cebDTO);
        ceb = cebRepository.save(ceb);
        return cebMapper.toDto(ceb);
    }

    /**
     * Get all the cebs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CebDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cebs");
        return cebRepository.findAll(pageable)
            .map(cebMapper::toDto);
    }


    /**
     * Get one ceb by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CebDTO> findOne(Long id) {
        log.debug("Request to get Ceb : {}", id);
        return cebRepository.findById(id)
            .map(cebMapper::toDto);
    }

    /**
     * Delete the ceb by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ceb : {}", id);        cebRepository.deleteById(id);
    }
}
