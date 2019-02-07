package com.test.service;

import com.test.domain.TypeEtablissement;
import com.test.repository.TypeEtablissementRepository;
import com.test.service.dto.TypeEtablissementDTO;
import com.test.service.mapper.TypeEtablissementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeEtablissement.
 */
@Service
@Transactional
public class TypeEtablissementService {

    private final Logger log = LoggerFactory.getLogger(TypeEtablissementService.class);

    private final TypeEtablissementRepository typeEtablissementRepository;

    private final TypeEtablissementMapper typeEtablissementMapper;

    public TypeEtablissementService(TypeEtablissementRepository typeEtablissementRepository, TypeEtablissementMapper typeEtablissementMapper) {
        this.typeEtablissementRepository = typeEtablissementRepository;
        this.typeEtablissementMapper = typeEtablissementMapper;
    }

    /**
     * Save a typeEtablissement.
     *
     * @param typeEtablissementDTO the entity to save
     * @return the persisted entity
     */
    public TypeEtablissementDTO save(TypeEtablissementDTO typeEtablissementDTO) {
        log.debug("Request to save TypeEtablissement : {}", typeEtablissementDTO);
        TypeEtablissement typeEtablissement = typeEtablissementMapper.toEntity(typeEtablissementDTO);
        typeEtablissement = typeEtablissementRepository.save(typeEtablissement);
        return typeEtablissementMapper.toDto(typeEtablissement);
    }

    /**
     * Get all the typeEtablissements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeEtablissementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeEtablissements");
        return typeEtablissementRepository.findAll(pageable)
            .map(typeEtablissementMapper::toDto);
    }


    /**
     * Get one typeEtablissement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeEtablissementDTO> findOne(Long id) {
        log.debug("Request to get TypeEtablissement : {}", id);
        return typeEtablissementRepository.findById(id)
            .map(typeEtablissementMapper::toDto);
    }

    /**
     * Delete the typeEtablissement by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeEtablissement : {}", id);        typeEtablissementRepository.deleteById(id);
    }
}
