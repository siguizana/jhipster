package com.test.service;

import com.test.domain.TypeCritere;
import com.test.repository.TypeCritereRepository;
import com.test.service.dto.TypeCritereDTO;
import com.test.service.mapper.TypeCritereMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeCritere.
 */
@Service
@Transactional
public class TypeCritereService {

    private final Logger log = LoggerFactory.getLogger(TypeCritereService.class);

    private final TypeCritereRepository typeCritereRepository;

    private final TypeCritereMapper typeCritereMapper;

    public TypeCritereService(TypeCritereRepository typeCritereRepository, TypeCritereMapper typeCritereMapper) {
        this.typeCritereRepository = typeCritereRepository;
        this.typeCritereMapper = typeCritereMapper;
    }

    /**
     * Save a typeCritere.
     *
     * @param typeCritereDTO the entity to save
     * @return the persisted entity
     */
    public TypeCritereDTO save(TypeCritereDTO typeCritereDTO) {
        log.debug("Request to save TypeCritere : {}", typeCritereDTO);
        TypeCritere typeCritere = typeCritereMapper.toEntity(typeCritereDTO);
        typeCritere = typeCritereRepository.save(typeCritere);
        return typeCritereMapper.toDto(typeCritere);
    }

    /**
     * Get all the typeCriteres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeCritereDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeCriteres");
        return typeCritereRepository.findAll(pageable)
            .map(typeCritereMapper::toDto);
    }


    /**
     * Get one typeCritere by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeCritereDTO> findOne(Long id) {
        log.debug("Request to get TypeCritere : {}", id);
        return typeCritereRepository.findById(id)
            .map(typeCritereMapper::toDto);
    }

    /**
     * Delete the typeCritere by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeCritere : {}", id);        typeCritereRepository.deleteById(id);
    }
}
