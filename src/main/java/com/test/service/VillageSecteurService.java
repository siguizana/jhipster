package com.test.service;

import com.test.domain.VillageSecteur;
import com.test.repository.VillageSecteurRepository;
import com.test.service.dto.VillageSecteurDTO;
import com.test.service.mapper.VillageSecteurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing VillageSecteur.
 */
@Service
@Transactional
public class VillageSecteurService {

    private final Logger log = LoggerFactory.getLogger(VillageSecteurService.class);

    private final VillageSecteurRepository villageSecteurRepository;

    private final VillageSecteurMapper villageSecteurMapper;

    public VillageSecteurService(VillageSecteurRepository villageSecteurRepository, VillageSecteurMapper villageSecteurMapper) {
        this.villageSecteurRepository = villageSecteurRepository;
        this.villageSecteurMapper = villageSecteurMapper;
    }

    /**
     * Save a villageSecteur.
     *
     * @param villageSecteurDTO the entity to save
     * @return the persisted entity
     */
    public VillageSecteurDTO save(VillageSecteurDTO villageSecteurDTO) {
        log.debug("Request to save VillageSecteur : {}", villageSecteurDTO);
        VillageSecteur villageSecteur = villageSecteurMapper.toEntity(villageSecteurDTO);
        villageSecteur = villageSecteurRepository.save(villageSecteur);
        return villageSecteurMapper.toDto(villageSecteur);
    }

    /**
     * Get all the villageSecteurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VillageSecteurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VillageSecteurs");
        return villageSecteurRepository.findAll(pageable)
            .map(villageSecteurMapper::toDto);
    }


    /**
     * Get one villageSecteur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<VillageSecteurDTO> findOne(Long id) {
        log.debug("Request to get VillageSecteur : {}", id);
        return villageSecteurRepository.findById(id)
            .map(villageSecteurMapper::toDto);
    }

    /**
     * Delete the villageSecteur by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VillageSecteur : {}", id);        villageSecteurRepository.deleteById(id);
    }
}
