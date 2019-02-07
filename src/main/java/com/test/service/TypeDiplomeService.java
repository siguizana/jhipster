package com.test.service;

import com.test.domain.TypeDiplome;
import com.test.repository.TypeDiplomeRepository;
import com.test.service.dto.TypeDiplomeDTO;
import com.test.service.mapper.TypeDiplomeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeDiplome.
 */
@Service
@Transactional
public class TypeDiplomeService {

    private final Logger log = LoggerFactory.getLogger(TypeDiplomeService.class);

    private final TypeDiplomeRepository typeDiplomeRepository;

    private final TypeDiplomeMapper typeDiplomeMapper;

    public TypeDiplomeService(TypeDiplomeRepository typeDiplomeRepository, TypeDiplomeMapper typeDiplomeMapper) {
        this.typeDiplomeRepository = typeDiplomeRepository;
        this.typeDiplomeMapper = typeDiplomeMapper;
    }

    /**
     * Save a typeDiplome.
     *
     * @param typeDiplomeDTO the entity to save
     * @return the persisted entity
     */
    public TypeDiplomeDTO save(TypeDiplomeDTO typeDiplomeDTO) {
        log.debug("Request to save TypeDiplome : {}", typeDiplomeDTO);
        TypeDiplome typeDiplome = typeDiplomeMapper.toEntity(typeDiplomeDTO);
        typeDiplome = typeDiplomeRepository.save(typeDiplome);
        return typeDiplomeMapper.toDto(typeDiplome);
    }

    /**
     * Get all the typeDiplomes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeDiplomeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeDiplomes");
        return typeDiplomeRepository.findAll(pageable)
            .map(typeDiplomeMapper::toDto);
    }


    /**
     * Get one typeDiplome by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeDiplomeDTO> findOne(Long id) {
        log.debug("Request to get TypeDiplome : {}", id);
        return typeDiplomeRepository.findById(id)
            .map(typeDiplomeMapper::toDto);
    }

    /**
     * Delete the typeDiplome by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeDiplome : {}", id);        typeDiplomeRepository.deleteById(id);
    }
}
