package com.test.web.rest;
import com.test.service.MembreJuryJuryService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.MembreJuryJuryDTO;
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
 * REST controller for managing MembreJuryJury.
 */
@RestController
@RequestMapping("/api")
public class MembreJuryJuryResource {

    private final Logger log = LoggerFactory.getLogger(MembreJuryJuryResource.class);

    private static final String ENTITY_NAME = "membreJuryJury";

    private final MembreJuryJuryService membreJuryJuryService;

    public MembreJuryJuryResource(MembreJuryJuryService membreJuryJuryService) {
        this.membreJuryJuryService = membreJuryJuryService;
    }

    /**
     * POST  /membre-jury-juries : Create a new membreJuryJury.
     *
     * @param membreJuryJuryDTO the membreJuryJuryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new membreJuryJuryDTO, or with status 400 (Bad Request) if the membreJuryJury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/membre-jury-juries")
    public ResponseEntity<MembreJuryJuryDTO> createMembreJuryJury(@RequestBody MembreJuryJuryDTO membreJuryJuryDTO) throws URISyntaxException {
        log.debug("REST request to save MembreJuryJury : {}", membreJuryJuryDTO);
        if (membreJuryJuryDTO.getId() != null) {
            throw new BadRequestAlertException("A new membreJuryJury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MembreJuryJuryDTO result = membreJuryJuryService.save(membreJuryJuryDTO);
        return ResponseEntity.created(new URI("/api/membre-jury-juries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /membre-jury-juries : Updates an existing membreJuryJury.
     *
     * @param membreJuryJuryDTO the membreJuryJuryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated membreJuryJuryDTO,
     * or with status 400 (Bad Request) if the membreJuryJuryDTO is not valid,
     * or with status 500 (Internal Server Error) if the membreJuryJuryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/membre-jury-juries")
    public ResponseEntity<MembreJuryJuryDTO> updateMembreJuryJury(@RequestBody MembreJuryJuryDTO membreJuryJuryDTO) throws URISyntaxException {
        log.debug("REST request to update MembreJuryJury : {}", membreJuryJuryDTO);
        if (membreJuryJuryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MembreJuryJuryDTO result = membreJuryJuryService.save(membreJuryJuryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, membreJuryJuryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /membre-jury-juries : get all the membreJuryJuries.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of membreJuryJuries in body
     */
    @GetMapping("/membre-jury-juries")
    public ResponseEntity<List<MembreJuryJuryDTO>> getAllMembreJuryJuries(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of MembreJuryJuries");
        Page<MembreJuryJuryDTO> page;
        if (eagerload) {
            page = membreJuryJuryService.findAllWithEagerRelationships(pageable);
        } else {
            page = membreJuryJuryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/membre-jury-juries?eagerload=%b", eagerload));
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /membre-jury-juries/:id : get the "id" membreJuryJury.
     *
     * @param id the id of the membreJuryJuryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the membreJuryJuryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/membre-jury-juries/{id}")
    public ResponseEntity<MembreJuryJuryDTO> getMembreJuryJury(@PathVariable Long id) {
        log.debug("REST request to get MembreJuryJury : {}", id);
        Optional<MembreJuryJuryDTO> membreJuryJuryDTO = membreJuryJuryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(membreJuryJuryDTO);
    }

    /**
     * DELETE  /membre-jury-juries/:id : delete the "id" membreJuryJury.
     *
     * @param id the id of the membreJuryJuryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/membre-jury-juries/{id}")
    public ResponseEntity<Void> deleteMembreJuryJury(@PathVariable Long id) {
        log.debug("REST request to delete MembreJuryJury : {}", id);
        membreJuryJuryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
