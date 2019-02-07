package com.test.service;

import com.test.domain.EpreuveSpecialiteOption;
import com.test.repository.EpreuveSpecialiteOptionRepository;
import com.test.service.dto.EpreuveSpecialiteOptionDTO;
import com.test.service.mapper.EpreuveSpecialiteOptionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing EpreuveSpecialiteOption.
 */
@Service
@Transactional
public class EpreuveSpecialiteOptionService {

    private final Logger log = LoggerFactory.getLogger(EpreuveSpecialiteOptionService.class);

    private final EpreuveSpecialiteOptionRepository epreuveSpecialiteOptionRepository;

    private final EpreuveSpecialiteOptionMapper epreuveSpecialiteOptionMapper;

    public EpreuveSpecialiteOptionService(EpreuveSpecialiteOptionRepository epreuveSpecialiteOptionRepository, EpreuveSpecialiteOptionMapper epreuveSpecialiteOptionMapper) {
        this.epreuveSpecialiteOptionRepository = epreuveSpecialiteOptionRepository;
        this.epreuveSpecialiteOptionMapper = epreuveSpecialiteOptionMapper;
    }

    /**
     * Save a epreuveSpecialiteOption.
     *
     * @param epreuveSpecialiteOptionDTO the entity to save
     * @return the persisted entity
     */
    public EpreuveSpecialiteOptionDTO save(EpreuveSpecialiteOptionDTO epreuveSpecialiteOptionDTO) {
        log.debug("Request to save EpreuveSpecialiteOption : {}", epreuveSpecialiteOptionDTO);
        EpreuveSpecialiteOption epreuveSpecialiteOption = epreuveSpecialiteOptionMapper.toEntity(epreuveSpecialiteOptionDTO);
        epreuveSpecialiteOption = epreuveSpecialiteOptionRepository.save(epreuveSpecialiteOption);
        return epreuveSpecialiteOptionMapper.toDto(epreuveSpecialiteOption);
    }

    /**
     * Get all the epreuveSpecialiteOptions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<EpreuveSpecialiteOptionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EpreuveSpecialiteOptions");
        return epreuveSpecialiteOptionRepository.findAll(pageable)
            .map(epreuveSpecialiteOptionMapper::toDto);
    }


    /**
     * Get one epreuveSpecialiteOption by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<EpreuveSpecialiteOptionDTO> findOne(Long id) {
        log.debug("Request to get EpreuveSpecialiteOption : {}", id);
        return epreuveSpecialiteOptionRepository.findById(id)
            .map(epreuveSpecialiteOptionMapper::toDto);
    }

    /**
     * Delete the epreuveSpecialiteOption by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete EpreuveSpecialiteOption : {}", id);        epreuveSpecialiteOptionRepository.deleteById(id);
    }
}
