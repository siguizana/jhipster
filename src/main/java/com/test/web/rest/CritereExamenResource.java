package com.test.web.rest;
import com.test.service.CritereExamenService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.CritereExamenDTO;
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
 * REST controller for managing CritereExamen.
 */
@RestController
@RequestMapping("/api")
public class CritereExamenResource {

    private final Logger log = LoggerFactory.getLogger(CritereExamenResource.class);

    private static final String ENTITY_NAME = "critereExamen";

    private final CritereExamenService critereExamenService;

    public CritereExamenResource(CritereExamenService critereExamenService) {
        this.critereExamenService = critereExamenService;
    }

    /**
     * POST  /critere-examen : Create a new critereExamen.
     *
     * @param critereExamenDTO the critereExamenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new critereExamenDTO, or with status 400 (Bad Request) if the critereExamen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/critere-examen")
    public ResponseEntity<CritereExamenDTO> createCritereExamen(@Valid @RequestBody CritereExamenDTO critereExamenDTO) throws URISyntaxException {
        log.debug("REST request to save CritereExamen : {}", critereExamenDTO);
        if (critereExamenDTO.getId() != null) {
            throw new BadRequestAlertException("A new critereExamen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CritereExamenDTO result = critereExamenService.save(critereExamenDTO);
        return ResponseEntity.created(new URI("/api/critere-examen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /critere-examen : Updates an existing critereExamen.
     *
     * @param critereExamenDTO the critereExamenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated critereExamenDTO,
     * or with status 400 (Bad Request) if the critereExamenDTO is not valid,
     * or with status 500 (Internal Server Error) if the critereExamenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/critere-examen")
    public ResponseEntity<CritereExamenDTO> updateCritereExamen(@Valid @RequestBody CritereExamenDTO critereExamenDTO) throws URISyntaxException {
        log.debug("REST request to update CritereExamen : {}", critereExamenDTO);
        if (critereExamenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CritereExamenDTO result = critereExamenService.save(critereExamenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, critereExamenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /critere-examen : get all the critereExamen.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of critereExamen in body
     */
    @GetMapping("/critere-examen")
    public ResponseEntity<List<CritereExamenDTO>> getAllCritereExamen(Pageable pageable) {
        log.debug("REST request to get a page of CritereExamen");
        Page<CritereExamenDTO> page = critereExamenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/critere-examen");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /critere-examen/:id : get the "id" critereExamen.
     *
     * @param id the id of the critereExamenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the critereExamenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/critere-examen/{id}")
    public ResponseEntity<CritereExamenDTO> getCritereExamen(@PathVariable Long id) {
        log.debug("REST request to get CritereExamen : {}", id);
        Optional<CritereExamenDTO> critereExamenDTO = critereExamenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(critereExamenDTO);
    }

    /**
     * DELETE  /critere-examen/:id : delete the "id" critereExamen.
     *
     * @param id the id of the critereExamenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/critere-examen/{id}")
    public ResponseEntity<Void> deleteCritereExamen(@PathVariable Long id) {
        log.debug("REST request to delete CritereExamen : {}", id);
        critereExamenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
