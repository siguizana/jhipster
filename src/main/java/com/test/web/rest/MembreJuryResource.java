package com.test.web.rest;
import com.test.service.MembreJuryService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.MembreJuryDTO;
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
 * REST controller for managing MembreJury.
 */
@RestController
@RequestMapping("/api")
public class MembreJuryResource {

    private final Logger log = LoggerFactory.getLogger(MembreJuryResource.class);

    private static final String ENTITY_NAME = "membreJury";

    private final MembreJuryService membreJuryService;

    public MembreJuryResource(MembreJuryService membreJuryService) {
        this.membreJuryService = membreJuryService;
    }

    /**
     * POST  /membre-juries : Create a new membreJury.
     *
     * @param membreJuryDTO the membreJuryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membreJuryDTO, or with status 400 (Bad Request) if the membreJury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/membre-juries")
    public ResponseEntity<MembreJuryDTO> createMembreJury(@Valid @RequestBody MembreJuryDTO membreJuryDTO) throws URISyntaxException {
        log.debug("REST request to save MembreJury : {}", membreJuryDTO);
        if (membreJuryDTO.getId() != null) {
            throw new BadRequestAlertException("A new membreJury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembreJuryDTO result = membreJuryService.save(membreJuryDTO);
        return ResponseEntity.created(new URI("/api/membre-juries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /membre-juries : Updates an existing membreJury.
     *
     * @param membreJuryDTO the membreJuryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membreJuryDTO,
     * or with status 400 (Bad Request) if the membreJuryDTO is not valid,
     * or with status 500 (Internal Server Error) if the membreJuryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/membre-juries")
    public ResponseEntity<MembreJuryDTO> updateMembreJury(@Valid @RequestBody MembreJuryDTO membreJuryDTO) throws URISyntaxException {
        log.debug("REST request to update MembreJury : {}", membreJuryDTO);
        if (membreJuryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MembreJuryDTO result = membreJuryService.save(membreJuryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, membreJuryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /membre-juries : get all the membreJuries.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of membreJuries in body
     */
    @GetMapping("/membre-juries")
    public ResponseEntity<List<MembreJuryDTO>> getAllMembreJuries(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of MembreJuries");
        Page<MembreJuryDTO> page;
        if (eagerload) {
            page = membreJuryService.findAllWithEagerRelationships(pageable);
        } else {
            page = membreJuryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/membre-juries?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /membre-juries/:id : get the "id" membreJury.
     *
     * @param id the id of the membreJuryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membreJuryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/membre-juries/{id}")
    public ResponseEntity<MembreJuryDTO> getMembreJury(@PathVariable Long id) {
        log.debug("REST request to get MembreJury : {}", id);
        Optional<MembreJuryDTO> membreJuryDTO = membreJuryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membreJuryDTO);
    }

    /**
     * DELETE  /membre-juries/:id : delete the "id" membreJury.
     *
     * @param id the id of the membreJuryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/membre-juries/{id}")
    public ResponseEntity<Void> deleteMembreJury(@PathVariable Long id) {
        log.debug("REST request to delete MembreJury : {}", id);
        membreJuryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
