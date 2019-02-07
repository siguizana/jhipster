package com.test.service;

import com.test.domain.Jury;
import com.test.repository.JuryRepository;
import com.test.service.dto.JuryDTO;
import com.test.service.mapper.JuryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Jury.
 */
@Service
@Transactional
public class JuryService {

    private final Logger log = LoggerFactory.getLogger(JuryService.class);

    private final JuryRepository juryRepository;

    private final JuryMapper juryMapper;

    public JuryService(JuryRepository juryRepository, JuryMapper juryMapper) {
        this.juryRepository = juryRepository;
        this.juryMapper = juryMapper;
    }

    /**
     * Save a jury.
     *
     * @param juryDTO the entity to save
     * @return the persisted entity
     */
    public JuryDTO save(JuryDTO juryDTO) {
        log.debug("Request to save Jury : {}", juryDTO);
        Jury jury = juryMapper.toEntity(juryDTO);
        jury = juryRepository.save(jury);
        return juryMapper.toDto(jury);
    }

    /**
     * Get all the juries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<JuryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Juries");
        return juryRepository.findAll(pageable)
            .map(juryMapper::toDto);
    }


    /**
     * Get one jury by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<JuryDTO> findOne(Long id) {
        log.debug("Request to get Jury : {}", id);
        return juryRepository.findById(id)
            .map(juryMapper::toDto);
    }

    /**
     * Delete the jury by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Jury : {}", id);        juryRepository.deleteById(id);
    }
}
