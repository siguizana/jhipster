package com.test.service;

import com.test.domain.TypeExamen;
import com.test.repository.TypeExamenRepository;
import com.test.service.dto.TypeExamenDTO;
import com.test.service.mapper.TypeExamenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeExamen.
 */
@Service
@Transactional
public class TypeExamenService {

    private final Logger log = LoggerFactory.getLogger(TypeExamenService.class);

    private final TypeExamenRepository typeExamenRepository;

    private final TypeExamenMapper typeExamenMapper;

    public TypeExamenService(TypeExamenRepository typeExamenRepository, TypeExamenMapper typeExamenMapper) {
        this.typeExamenRepository = typeExamenRepository;
        this.typeExamenMapper = typeExamenMapper;
    }

    /**
     * Save a typeExamen.
     *
     * @param typeExamenDTO the entity to save
     * @return the persisted entity
     */
    public TypeExamenDTO save(TypeExamenDTO typeExamenDTO) {
        log.debug("Request to save TypeExamen : {}", typeExamenDTO);
        TypeExamen typeExamen = typeExamenMapper.toEntity(typeExamenDTO);
        typeExamen = typeExamenRepository.save(typeExamen);
        return typeExamenMapper.toDto(typeExamen);
    }

    /**
     * Get all the typeExamen.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeExamenDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeExamen");
        return typeExamenRepository.findAll(pageable)
            .map(typeExamenMapper::toDto);
    }


    /**
     * Get one typeExamen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeExamenDTO> findOne(Long id) {
        log.debug("Request to get TypeExamen : {}", id);
        return typeExamenRepository.findById(id)
            .map(typeExamenMapper::toDto);
    }

    /**
     * Delete the typeExamen by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeExamen : {}", id);        typeExamenRepository.deleteById(id);
    }
}
