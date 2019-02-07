package com.test.service;

import com.test.domain.NoteMembreCritere;
import com.test.repository.NoteMembreCritereRepository;
import com.test.service.dto.NoteMembreCritereDTO;
import com.test.service.mapper.NoteMembreCritereMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing NoteMembreCritere.
 */
@Service
@Transactional
public class NoteMembreCritereService {

    private final Logger log = LoggerFactory.getLogger(NoteMembreCritereService.class);

    private final NoteMembreCritereRepository noteMembreCritereRepository;

    private final NoteMembreCritereMapper noteMembreCritereMapper;

    public NoteMembreCritereService(NoteMembreCritereRepository noteMembreCritereRepository, NoteMembreCritereMapper noteMembreCritereMapper) {
        this.noteMembreCritereRepository = noteMembreCritereRepository;
        this.noteMembreCritereMapper = noteMembreCritereMapper;
    }

    /**
     * Save a noteMembreCritere.
     *
     * @param noteMembreCritereDTO the entity to save
     * @return the persisted entity
     */
    public NoteMembreCritereDTO save(NoteMembreCritereDTO noteMembreCritereDTO) {
        log.debug("Request to save NoteMembreCritere : {}", noteMembreCritereDTO);
        NoteMembreCritere noteMembreCritere = noteMembreCritereMapper.toEntity(noteMembreCritereDTO);
        noteMembreCritere = noteMembreCritereRepository.save(noteMembreCritere);
        return noteMembreCritereMapper.toDto(noteMembreCritere);
    }

    /**
     * Get all the noteMembreCriteres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<NoteMembreCritereDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NoteMembreCriteres");
        return noteMembreCritereRepository.findAll(pageable)
            .map(noteMembreCritereMapper::toDto);
    }


    /**
     * Get one noteMembreCritere by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<NoteMembreCritereDTO> findOne(Long id) {
        log.debug("Request to get NoteMembreCritere : {}", id);
        return noteMembreCritereRepository.findById(id)
            .map(noteMembreCritereMapper::toDto);
    }

    /**
     * Delete the noteMembreCritere by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete NoteMembreCritere : {}", id);        noteMembreCritereRepository.deleteById(id);
    }
}
