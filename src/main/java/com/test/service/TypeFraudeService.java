package com.test.service;

import com.test.domain.TypeFraude;
import com.test.repository.TypeFraudeRepository;
import com.test.service.dto.TypeFraudeDTO;
import com.test.service.mapper.TypeFraudeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TypeFraude.
 */
@Service
@Transactional
public class TypeFraudeService {

    private final Logger log = LoggerFactory.getLogger(TypeFraudeService.class);

    private final TypeFraudeRepository typeFraudeRepository;

    private final TypeFraudeMapper typeFraudeMapper;

    public TypeFraudeService(TypeFraudeRepository typeFraudeRepository, TypeFraudeMapper typeFraudeMapper) {
        this.typeFraudeRepository = typeFraudeRepository;
        this.typeFraudeMapper = typeFraudeMapper;
    }

    /**
     * Save a typeFraude.
     *
     * @param typeFraudeDTO the entity to save
     * @return the persisted entity
     */
    public TypeFraudeDTO save(TypeFraudeDTO typeFraudeDTO) {
        log.debug("Request to save TypeFraude : {}", typeFraudeDTO);
        TypeFraude typeFraude = typeFraudeMapper.toEntity(typeFraudeDTO);
        typeFraude = typeFraudeRepository.save(typeFraude);
        return typeFraudeMapper.toDto(typeFraude);
    }

    /**
     * Get all the typeFraudes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TypeFraudeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TypeFraudes");
        return typeFraudeRepository.findAll(pageable)
            .map(typeFraudeMapper::toDto);
    }


    /**
     * Get one typeFraude by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TypeFraudeDTO> findOne(Long id) {
        log.debug("Request to get TypeFraude : {}", id);
        return typeFraudeRepository.findById(id)
            .map(typeFraudeMapper::toDto);
    }

    /**
     * Delete the typeFraude by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeFraude : {}", id);        typeFraudeRepository.deleteById(id);
    }
}
