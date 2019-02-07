package com.test.service;

import com.test.domain.Commune;
import com.test.repository.CommuneRepository;
import com.test.service.dto.CommuneDTO;
import com.test.service.mapper.CommuneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Commune.
 */
@Service
@Transactional
public class CommuneService {

    private final Logger log = LoggerFactory.getLogger(CommuneService.class);

    private final CommuneRepository communeRepository;

    private final CommuneMapper communeMapper;

    public CommuneService(CommuneRepository communeRepository, CommuneMapper communeMapper) {
        this.communeRepository = communeRepository;
        this.communeMapper = communeMapper;
    }

    /**
     * Save a commune.
     *
     * @param communeDTO the entity to save
     * @return the persisted entity
     */
    public CommuneDTO save(CommuneDTO communeDTO) {
        log.debug("Request to save Commune : {}", communeDTO);
        Commune commune = communeMapper.toEntity(communeDTO);
        commune = communeRepository.save(commune);
        return communeMapper.toDto(commune);
    }

    /**
     * Get all the communes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommuneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Communes");
        return communeRepository.findAll(pageable)
            .map(communeMapper::toDto);
    }


    /**
     * Get one commune by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CommuneDTO> findOne(Long id) {
        log.debug("Request to get Commune : {}", id);
        return communeRepository.findById(id)
            .map(communeMapper::toDto);
    }

    /**
     * Delete the commune by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Commune : {}", id);        communeRepository.deleteById(id);
    }
}
