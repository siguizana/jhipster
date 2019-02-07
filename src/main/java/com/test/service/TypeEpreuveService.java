package com.test.service;

import com.test.domain.TypeEpreuve;
import com.test.repository.TypeEpreuveRepository;
import com.test.service.dto.TypeEpreuveDTO;
import com.test.service.mapper.TypeEpreuveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeEpreuve.
 */
@Service
@Transactional
public class TypeEpreuveService {

    private final Logger log = LoggerFactory.getLogger(TypeEpreuveService.class);

    private final TypeEpreuveRepository typeEpreuveRepository;

    private final TypeEpreuveMapper typeEpreuveMapper;

    public TypeEpreuveService(TypeEpreuveRepository typeEpreuveRepository, TypeEpreuveMapper typeEpreuveMapper) {
        this.typeEpreuveRepository = typeEpreuveRepository;
        this.typeEpreuveMapper = typeEpreuveMapper;
    }

    /**
     * Save a typeEpreuve.
     *
     * @param typeEpreuveDTO the entity to save
     * @return the persisted entity
     */
    public TypeEpreuveDTO save(TypeEpreuveDTO typeEpreuveDTO) {
        log.debug("Request to save TypeEpreuve : {}", typeEpreuveDTO);
        TypeEpreuve typeEpreuve = typeEpreuveMapper.toEntity(typeEpreuveDTO);
        typeEpreuve = typeEpreuveRepository.save(typeEpreuve);
        return typeEpreuveMapper.toDto(typeEpreuve);
    }

    /**
     * Get all the typeEpreuves.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeEpreuveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeEpreuves");
        return typeEpreuveRepository.findAll(pageable)
            .map(typeEpreuveMapper::toDto);
    }


    /**
     * Get one typeEpreuve by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeEpreuveDTO> findOne(Long id) {
        log.debug("Request to get TypeEpreuve : {}", id);
        return typeEpreuveRepository.findById(id)
            .map(typeEpreuveMapper::toDto);
    }

    /**
     * Delete the typeEpreuve by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeEpreuve : {}", id);        typeEpreuveRepository.deleteById(id);
    }
}
