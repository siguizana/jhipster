package com.test.service;

import com.test.domain.GroupeEpreuve;
import com.test.repository.GroupeEpreuveRepository;
import com.test.service.dto.GroupeEpreuveDTO;
import com.test.service.mapper.GroupeEpreuveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing GroupeEpreuve.
 */
@Service
@Transactional
public class GroupeEpreuveService {

    private final Logger log = LoggerFactory.getLogger(GroupeEpreuveService.class);

    private final GroupeEpreuveRepository groupeEpreuveRepository;

    private final GroupeEpreuveMapper groupeEpreuveMapper;

    public GroupeEpreuveService(GroupeEpreuveRepository groupeEpreuveRepository, GroupeEpreuveMapper groupeEpreuveMapper) {
        this.groupeEpreuveRepository = groupeEpreuveRepository;
        this.groupeEpreuveMapper = groupeEpreuveMapper;
    }

    /**
     * Save a groupeEpreuve.
     *
     * @param groupeEpreuveDTO the entity to save
     * @return the persisted entity
     */
    public GroupeEpreuveDTO save(GroupeEpreuveDTO groupeEpreuveDTO) {
        log.debug("Request to save GroupeEpreuve : {}", groupeEpreuveDTO);
        GroupeEpreuve groupeEpreuve = groupeEpreuveMapper.toEntity(groupeEpreuveDTO);
        groupeEpreuve = groupeEpreuveRepository.save(groupeEpreuve);
        return groupeEpreuveMapper.toDto(groupeEpreuve);
    }

    /**
     * Get all the groupeEpreuves.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<GroupeEpreuveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GroupeEpreuves");
        return groupeEpreuveRepository.findAll(pageable)
            .map(groupeEpreuveMapper::toDto);
    }


    /**
     * Get one groupeEpreuve by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<GroupeEpreuveDTO> findOne(Long id) {
        log.debug("Request to get GroupeEpreuve : {}", id);
        return groupeEpreuveRepository.findById(id)
            .map(groupeEpreuveMapper::toDto);
    }

    /**
     * Delete the groupeEpreuve by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupeEpreuve : {}", id);        groupeEpreuveRepository.deleteById(id);
    }
}
