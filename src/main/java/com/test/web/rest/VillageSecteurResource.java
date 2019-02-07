package com.test.web.rest;
import com.test.service.VillageSecteurService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.VillageSecteurDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VillageSecteur.
 */
@RestController
@RequestMapping("/api")
public class VillageSecteurResource {

    private final Logger log = LoggerFactory.getLogger(VillageSecteurResource.class);

    private static final String ENTITY_NAME = "villageSecteur";

    private final VillageSecteurService villageSecteurService;

    public VillageSecteurResource(VillageSecteurService villageSecteurService) {
        this.villageSecteurService = villageSecteurService;
    }

    /**
     * POST  /village-secteurs : Create a new villageSecteur.
     *
     * @param villageSecteurDTO the villageSecteurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new villageSecteurDTO, or with status 400 (Bad Request) if the villageSecteur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/village-secteurs")
    public ResponseEntity<VillageSecteurDTO> createVillageSecteur(@Valid @RequestBody VillageSecteurDTO villageSecteurDTO) throws URISyntaxException {
        log.debug("REST request to save VillageSecteur : {}", villageSecteurDTO);
        if (villageSecteurDTO.getId() != null) {
            throw new BadRequestAlertException("A new villageSecteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VillageSecteurDTO result = villageSecteurService.save(villageSecteurDTO);
        return ResponseEntity.created(new URI("/api/village-secteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /village-secteurs : Updates an existing villageSecteur.
     *
     * @param villageSecteurDTO the villageSecteurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated villageSecteurDTO,
     * or with status 400 (Bad Request) if the villageSecteurDTO is not valid,
     * or with status 500 (Internal Server Error) if the villageSecteurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/village-secteurs")
    public ResponseEntity<VillageSecteurDTO> updateVillageSecteur(@Valid @RequestBody VillageSecteurDTO villageSecteurDTO) throws URISyntaxException {
        log.debug("REST request to update VillageSecteur : {}", villageSecteurDTO);
        if (villageSecteurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VillageSecteurDTO result = villageSecteurService.save(villageSecteurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, villageSecteurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /village-secteurs : get all the villageSecteurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of villageSecteurs in body
     */
    @GetMapping("/village-secteurs")
    public ResponseEntity<List<VillageSecteurDTO>> getAllVillageSecteurs(Pageable pageable) {
        log.debug("REST request to get a page of VillageSecteurs");
        Page<VillageSecteurDTO> page = villageSecteurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/village-secteurs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /village-secteurs/:id : get the "id" villageSecteur.
     *
     * @param id the id of the villageSecteurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the villageSecteurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/village-secteurs/{id}")
    public ResponseEntity<VillageSecteurDTO> getVillageSecteur(@PathVariable Long id) {
        log.debug("REST request to get VillageSecteur : {}", id);
        Optional<VillageSecteurDTO> villageSecteurDTO = villageSecteurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(villageSecteurDTO);
    }

    /**
     * DELETE  /village-secteurs/:id : delete the "id" villageSecteur.
     *
     * @param id the id of the villageSecteurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/village-secteurs/{id}")
    public ResponseEntity<Void> deleteVillageSecteur(@PathVariable Long id) {
        log.debug("REST request to delete VillageSecteur : {}", id);
        villageSecteurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
