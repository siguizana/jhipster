package com.test.web.rest;
import com.test.service.SalleCompositionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.SalleCompositionDTO;
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
 * REST controller for managing SalleComposition.
 */
@RestController
@RequestMapping("/api")
public class SalleCompositionResource {

    private final Logger log = LoggerFactory.getLogger(SalleCompositionResource.class);

    private static final String ENTITY_NAME = "salleComposition";

    private final SalleCompositionService salleCompositionService;

    public SalleCompositionResource(SalleCompositionService salleCompositionService) {
        this.salleCompositionService = salleCompositionService;
    }

    /**
     * POST  /salle-compositions : Create a new salleComposition.
     *
     * @param salleCompositionDTO the salleCompositionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salleCompositionDTO, or with status 400 (Bad Request) if the salleComposition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salle-compositions")
    public ResponseEntity<SalleCompositionDTO> createSalleComposition(@Valid @RequestBody SalleCompositionDTO salleCompositionDTO) throws URISyntaxException {
        log.debug("REST request to save SalleComposition : {}", salleCompositionDTO);
        if (salleCompositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new salleComposition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalleCompositionDTO result = salleCompositionService.save(salleCompositionDTO);
        return ResponseEntity.created(new URI("/api/salle-compositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salle-compositions : Updates an existing salleComposition.
     *
     * @param salleCompositionDTO the salleCompositionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salleCompositionDTO,
     * or with status 400 (Bad Request) if the salleCompositionDTO is not valid,
     * or with status 500 (Internal Server Error) if the salleCompositionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salle-compositions")
    public ResponseEntity<SalleCompositionDTO> updateSalleComposition(@Valid @RequestBody SalleCompositionDTO salleCompositionDTO) throws URISyntaxException {
        log.debug("REST request to update SalleComposition : {}", salleCompositionDTO);
        if (salleCompositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalleCompositionDTO result = salleCompositionService.save(salleCompositionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salleCompositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salle-compositions : get all the salleCompositions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of salleCompositions in body
     */
    @GetMapping("/salle-compositions")
    public ResponseEntity<List<SalleCompositionDTO>> getAllSalleCompositions(Pageable pageable) {
        log.debug("REST request to get a page of SalleCompositions");
        Page<SalleCompositionDTO> page = salleCompositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salle-compositions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /salle-compositions/:id : get the "id" salleComposition.
     *
     * @param id the id of the salleCompositionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salleCompositionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/salle-compositions/{id}")
    public ResponseEntity<SalleCompositionDTO> getSalleComposition(@PathVariable Long id) {
        log.debug("REST request to get SalleComposition : {}", id);
        Optional<SalleCompositionDTO> salleCompositionDTO = salleCompositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salleCompositionDTO);
    }

    /**
     * DELETE  /salle-compositions/:id : delete the "id" salleComposition.
     *
     * @param id the id of the salleCompositionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salle-compositions/{id}")
    public ResponseEntity<Void> deleteSalleComposition(@PathVariable Long id) {
        log.debug("REST request to delete SalleComposition : {}", id);
        salleCompositionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
