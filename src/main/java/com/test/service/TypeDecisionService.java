package com.test.service;

import com.test.domain.TypeDecision;
import com.test.repository.TypeDecisionRepository;
import com.test.service.dto.TypeDecisionDTO;
import com.test.service.mapper.TypeDecisionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeDecision.
 */
@Service
@Transactional
public class TypeDecisionService {

    private final Logger log = LoggerFactory.getLogger(TypeDecisionService.class);

    private final TypeDecisionRepository typeDecisionRepository;

    private final TypeDecisionMapper typeDecisionMapper;

    public TypeDecisionService(TypeDecisionRepository typeDecisionRepository, TypeDecisionMapper typeDecisionMapper) {
        this.typeDecisionRepository = typeDecisionRepository;
        this.typeDecisionMapper = typeDecisionMapper;
    }

    /**
     * Save a typeDecision.
     *
     * @param typeDecisionDTO the entity to save
     * @return the persisted entity
     */
    public TypeDecisionDTO save(TypeDecisionDTO typeDecisionDTO) {
        log.debug("Request to save TypeDecision : {}", typeDecisionDTO);
        TypeDecision typeDecision = typeDecisionMapper.toEntity(typeDecisionDTO);
        typeDecision = typeDecisionRepository.save(typeDecision);
        return typeDecisionMapper.toDto(typeDecision);
    }

    /**
     * Get all the typeDecisions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeDecisionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeDecisions");
        return typeDecisionRepository.findAll(pageable)
            .map(typeDecisionMapper::toDto);
    }


    /**
     * Get one typeDecision by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeDecisionDTO> findOne(Long id) {
        log.debug("Request to get TypeDecision : {}", id);
        return typeDecisionRepository.findById(id)
            .map(typeDecisionMapper::toDto);
    }

    /**
     * Delete the typeDecision by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeDecision : {}", id);        typeDecisionRepository.deleteById(id);
    }
}
