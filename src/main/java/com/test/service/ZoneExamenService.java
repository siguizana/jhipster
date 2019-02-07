package com.test.service;

import com.test.domain.ZoneExamen;
import com.test.repository.ZoneExamenRepository;
import com.test.service.dto.ZoneExamenDTO;
import com.test.service.mapper.ZoneExamenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ZoneExamen.
 */
@Service
@Transactional
public class ZoneExamenService {

    private final Logger log = LoggerFactory.getLogger(ZoneExamenService.class);

    private final ZoneExamenRepository zoneExamenRepository;

    private final ZoneExamenMapper zoneExamenMapper;

    public ZoneExamenService(ZoneExamenRepository zoneExamenRepository, ZoneExamenMapper zoneExamenMapper) {
        this.zoneExamenRepository = zoneExamenRepository;
        this.zoneExamenMapper = zoneExamenMapper;
    }

    /**
     * Save a zoneExamen.
     *
     * @param zoneExamenDTO the entity to save
     * @return the persisted entity
     */
    public ZoneExamenDTO save(ZoneExamenDTO zoneExamenDTO) {
        log.debug("Request to save ZoneExamen : {}", zoneExamenDTO);
        ZoneExamen zoneExamen = zoneExamenMapper.toEntity(zoneExamenDTO);
        zoneExamen = zoneExamenRepository.save(zoneExamen);
        return zoneExamenMapper.toDto(zoneExamen);
    }

    /**
     * Get all the zoneExamen.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ZoneExamenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ZoneExamen");
        return zoneExamenRepository.findAll(pageable)
            .map(zoneExamenMapper::toDto);
    }


    /**
     * Get one zoneExamen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ZoneExamenDTO> findOne(Long id) {
        log.debug("Request to get ZoneExamen : {}", id);
        return zoneExamenRepository.findById(id)
            .map(zoneExamenMapper::toDto);
    }

    /**
     * Delete the zoneExamen by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ZoneExamen : {}", id);        zoneExamenRepository.deleteById(id);
    }
}
