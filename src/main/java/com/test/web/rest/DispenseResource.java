package com.test.web.rest;
import com.test.service.DispenseService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.DispenseDTO;
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
 * REST controller for managing Dispense.
 */
@RestController
@RequestMapping("/api")
public class DispenseResource {

    private final Logger log = LoggerFactory.getLogger(DispenseResource.class);

    private static final String ENTITY_NAME = "dispense";

    private final DispenseService dispenseService;

    public DispenseResource(DispenseService dispenseService) {
        this.dispenseService = dispenseService;
    }

    /**
     * POST  /dispenses : Create a new dispense.
     *
     * @param dispenseDTO the dispenseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dispenseDTO, or with status 400 (Bad Request) if the dispense has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dispenses")
    public ResponseEntity<DispenseDTO> createDispense(@Valid @RequestBody DispenseDTO dispenseDTO) throws URISyntaxException {
        log.debug("REST request to save Dispense : {}", dispenseDTO);
        if (dispenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DispenseDTO result = dispenseService.save(dispenseDTO);
        return ResponseEntity.created(new URI("/api/dispenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dispenses : Updates an existing dispense.
     *
     * @param dispenseDTO the dispenseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dispenseDTO,
     * or with status 400 (Bad Request) if the dispenseDTO is not valid,
     * or with status 500 (Internal Server Error) if the dispenseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dispenses")
    public ResponseEntity<DispenseDTO> updateDispense(@Valid @RequestBody DispenseDTO dispenseDTO) throws URISyntaxException {
        log.debug("REST request to update Dispense : {}", dispenseDTO);
        if (dispenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DispenseDTO result = dispenseService.save(dispenseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dispenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dispenses : get all the dispenses.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of dispenses in body
     */
    @GetMapping("/dispenses")
    public ResponseEntity<List<DispenseDTO>> getAllDispenses(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Dispenses");
        Page<DispenseDTO> page;
        if (eagerload) {
            page = dispenseService.findAllWithEagerRelationships(pageable);
        } else {
            page = dispenseService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/dispenses?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /dispenses/:id : get the "id" dispense.
     *
     * @param id the id of the dispenseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dispenseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/dispenses/{id}")
    public ResponseEntity<DispenseDTO> getDispense(@PathVariable Long id) {
        log.debug("REST request to get Dispense : {}", id);
        Optional<DispenseDTO> dispenseDTO = dispenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispenseDTO);
    }

    /**
     * DELETE  /dispenses/:id : delete the "id" dispense.
     *
     * @param id the id of the dispenseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dispenses/{id}")
    public ResponseEntity<Void> deleteDispense(@PathVariable Long id) {
        log.debug("REST request to delete Dispense : {}", id);
        dispenseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
