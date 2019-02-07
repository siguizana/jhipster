package com.test.web.rest;
import com.test.service.SurveilleService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.SurveilleDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Surveille.
 */
@RestController
@RequestMapping("/api")
public class SurveilleResource {

    private final Logger log = LoggerFactory.getLogger(SurveilleResource.class);

    private static final String ENTITY_NAME = "surveille";

    private final SurveilleService surveilleService;

    public SurveilleResource(SurveilleService surveilleService) {
        this.surveilleService = surveilleService;
    }

    /**
     * POST  /surveilles : Create a new surveille.
     *
     * @param surveilleDTO the surveilleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new surveilleDTO, or with status 400 (Bad Request) if the surveille has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/surveilles")
    public ResponseEntity<SurveilleDTO> createSurveille(@RequestBody SurveilleDTO surveilleDTO) throws URISyntaxException {
        log.debug("REST request to save Surveille : {}", surveilleDTO);
        if (surveilleDTO.getId() != null) {
            throw new BadRequestAlertException("A new surveille cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SurveilleDTO result = surveilleService.save(surveilleDTO);
        return ResponseEntity.created(new URI("/api/surveilles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /surveilles : Updates an existing surveille.
     *
     * @param surveilleDTO the surveilleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated surveilleDTO,
     * or with status 400 (Bad Request) if the surveilleDTO is not valid,
     * or with status 500 (Internal Server Error) if the surveilleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/surveilles")
    public ResponseEntity<SurveilleDTO> updateSurveille(@RequestBody SurveilleDTO surveilleDTO) throws URISyntaxException {
        log.debug("REST request to update Surveille : {}", surveilleDTO);
        if (surveilleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SurveilleDTO result = surveilleService.save(surveilleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, surveilleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /surveilles : get all the surveilles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of surveilles in body
     */
    @GetMapping("/surveilles")
    public ResponseEntity<List<SurveilleDTO>> getAllSurveilles(Pageable pageable) {
        log.debug("REST request to get a page of Surveilles");
        Page<SurveilleDTO> page = surveilleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/surveilles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /surveilles/:id : get the "id" surveille.
     *
     * @param id the id of the surveilleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the surveilleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/surveilles/{id}")
    public ResponseEntity<SurveilleDTO> getSurveille(@PathVariable Long id) {
        log.debug("REST request to get Surveille : {}", id);
        Optional<SurveilleDTO> surveilleDTO = surveilleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(surveilleDTO);
    }

    /**
     * DELETE  /surveilles/:id : delete the "id" surveille.
     *
     * @param id the id of the surveilleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/surveilles/{id}")
    public ResponseEntity<Void> deleteSurveille(@PathVariable Long id) {
        log.debug("REST request to delete Surveille : {}", id);
        surveilleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
