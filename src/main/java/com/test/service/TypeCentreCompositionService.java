package com.test.service;

import com.test.domain.TypeCentreComposition;
import com.test.repository.TypeCentreCompositionRepository;
import com.test.service.dto.TypeCentreCompositionDTO;
import com.test.service.mapper.TypeCentreCompositionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeCentreComposition.
 */
@Service
@Transactional
public class TypeCentreCompositionService {

    private final Logger log = LoggerFactory.getLogger(TypeCentreCompositionService.class);

    private final TypeCentreCompositionRepository typeCentreCompositionRepository;

    private final TypeCentreCompositionMapper typeCentreCompositionMapper;

    public TypeCentreCompositionService(TypeCentreCompositionRepository typeCentreCompositionRepository, TypeCentreCompositionMapper typeCentreCompositionMapper) {
        this.typeCentreCompositionRepository = typeCentreCompositionRepository;
        this.typeCentreCompositionMapper = typeCentreCompositionMapper;
    }

    /**
     * Save a typeCentreComposition.
     *
     * @param typeCentreCompositionDTO the entity to save
     * @return the persisted entity
     */
    public TypeCentreCompositionDTO save(TypeCentreCompositionDTO typeCentreCompositionDTO) {
        log.debug("Request to save TypeCentreComposition : {}", typeCentreCompositionDTO);
        TypeCentreComposition typeCentreComposition = typeCentreCompositionMapper.toEntity(typeCentreCompositionDTO);
        typeCentreComposition = typeCentreCompositionRepository.save(typeCentreComposition);
        return typeCentreCompositionMapper.toDto(typeCentreComposition);
    }

    /**
     * Get all the typeCentreCompositions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeCentreCompositionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeCentreCompositions");
        return typeCentreCompositionRepository.findAll(pageable)
            .map(typeCentreCompositionMapper::toDto);
    }


    /**
     * Get one typeCentreComposition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeCentreCompositionDTO> findOne(Long id) {
        log.debug("Request to get TypeCentreComposition : {}", id);
        return typeCentreCompositionRepository.findById(id)
            .map(typeCentreCompositionMapper::toDto);
    }

    /**
     * Delete the typeCentreComposition by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeCentreComposition : {}", id);        typeCentreCompositionRepository.deleteById(id);
    }
}
