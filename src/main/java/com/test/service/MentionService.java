package com.test.service;

import com.test.domain.Mention;
import com.test.repository.MentionRepository;
import com.test.service.dto.MentionDTO;
import com.test.service.mapper.MentionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Mention.
 */
@Service
@Transactional
public class MentionService {

    private final Logger log = LoggerFactory.getLogger(MentionService.class);

    private final MentionRepository mentionRepository;

    private final MentionMapper mentionMapper;

    public MentionService(MentionRepository mentionRepository, MentionMapper mentionMapper) {
        this.mentionRepository = mentionRepository;
        this.mentionMapper = mentionMapper;
    }

    /**
     * Save a mention.
     *
     * @param mentionDTO the entity to save
     * @return the persisted entity
     */
    public MentionDTO save(MentionDTO mentionDTO) {
        log.debug("Request to save Mention : {}", mentionDTO);
        Mention mention = mentionMapper.toEntity(mentionDTO);
        mention = mentionRepository.save(mention);
        return mentionMapper.toDto(mention);
    }

    /**
     * Get all the mentions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MentionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Mentions");
        return mentionRepository.findAll(pageable)
            .map(mentionMapper::toDto);
    }


    /**
     * Get one mention by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<MentionDTO> findOne(Long id) {
        log.debug("Request to get Mention : {}", id);
        return mentionRepository.findById(id)
            .map(mentionMapper::toDto);
    }

    /**
     * Delete the mention by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Mention : {}", id);        mentionRepository.deleteById(id);
    }
}
