package com.test.service;

import com.test.domain.TypeMembreJury;
import com.test.repository.TypeMembreJuryRepository;
import com.test.service.dto.TypeMembreJuryDTO;
import com.test.service.mapper.TypeMembreJuryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeMembreJury.
 */
@Service
@Transactional
public class TypeMembreJuryService {

    private final Logger log = LoggerFactory.getLogger(TypeMembreJuryService.class);

    private final TypeMembreJuryRepository typeMembreJuryRepository;

    private final TypeMembreJuryMapper typeMembreJuryMapper;

    public TypeMembreJuryService(TypeMembreJuryRepository typeMembreJuryRepository, TypeMembreJuryMapper typeMembreJuryMapper) {
        this.typeMembreJuryRepository = typeMembreJuryRepository;
        this.typeMembreJuryMapper = typeMembreJuryMapper;
    }

    /**
     * Save a typeMembreJury.
     *
     * @param typeMembreJuryDTO the entity to save
     * @return the persisted entity
     */
    public TypeMembreJuryDTO save(TypeMembreJuryDTO typeMembreJuryDTO) {
        log.debug("Request to save TypeMembreJury : {}", typeMembreJuryDTO);
        TypeMembreJury typeMembreJury = typeMembreJuryMapper.toEntity(typeMembreJuryDTO);
        typeMembreJury = typeMembreJuryRepository.save(typeMembreJury);
        return typeMembreJuryMapper.toDto(typeMembreJury);
    }

    /**
     * Get all the typeMembreJuries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeMembreJuryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeMembreJuries");
        return typeMembreJuryRepository.findAll(pageable)
            .map(typeMembreJuryMapper::toDto);
    }

    /**
     * Get all the TypeMembreJury with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<TypeMembreJuryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return typeMembreJuryRepository.findAllWithEagerRelationships(pageable).map(typeMembreJuryMapper::toDto);
    }
    

    /**
     * Get one typeMembreJury by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeMembreJuryDTO> findOne(Long id) {
        log.debug("Request to get TypeMembreJury : {}", id);
        return typeMembreJuryRepository.findOneWithEagerRelationships(id)
            .map(typeMembreJuryMapper::toDto);
    }

    /**
     * Delete the typeMembreJury by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeMembreJury : {}", id);        typeMembreJuryRepository.deleteById(id);
    }
}
