package com.test.service;

import com.test.domain.SpecialiteOption;
import com.test.repository.SpecialiteOptionRepository;
import com.test.service.dto.SpecialiteOptionDTO;
import com.test.service.mapper.SpecialiteOptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SpecialiteOption.
 */
@Service
@Transactional
public class SpecialiteOptionService {

    private final Logger log = LoggerFactory.getLogger(SpecialiteOptionService.class);

    private final SpecialiteOptionRepository specialiteOptionRepository;

    private final SpecialiteOptionMapper specialiteOptionMapper;

    public SpecialiteOptionService(SpecialiteOptionRepository specialiteOptionRepository, SpecialiteOptionMapper specialiteOptionMapper) {
        this.specialiteOptionRepository = specialiteOptionRepository;
        this.specialiteOptionMapper = specialiteOptionMapper;
    }

    /**
     * Save a specialiteOption.
     *
     * @param specialiteOptionDTO the entity to save
     * @return the persisted entity
     */
    public SpecialiteOptionDTO save(SpecialiteOptionDTO specialiteOptionDTO) {
        log.debug("Request to save SpecialiteOption : {}", specialiteOptionDTO);
        SpecialiteOption specialiteOption = specialiteOptionMapper.toEntity(specialiteOptionDTO);
        specialiteOption = specialiteOptionRepository.save(specialiteOption);
        return specialiteOptionMapper.toDto(specialiteOption);
    }

    /**
     * Get all the specialiteOptions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SpecialiteOptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SpecialiteOptions");
        return specialiteOptionRepository.findAll(pageable)
            .map(specialiteOptionMapper::toDto);
    }


    /**
     * Get one specialiteOption by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<SpecialiteOptionDTO> findOne(Long id) {
        log.debug("Request to get SpecialiteOption : {}", id);
        return specialiteOptionRepository.findById(id)
            .map(specialiteOptionMapper::toDto);
    }

    /**
     * Delete the specialiteOption by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SpecialiteOption : {}", id);        specialiteOptionRepository.deleteById(id);
    }
}
