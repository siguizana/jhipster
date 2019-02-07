package com.test.web.rest;
import com.test.service.RetraitDiplomeService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.RetraitDiplomeDTO;
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
 * REST controller for managing RetraitDiplome.
 */
@RestController
@RequestMapping("/api")
public class RetraitDiplomeResource {

    private final Logger log = LoggerFactory.getLogger(RetraitDiplomeResource.class);

    private static final String ENTITY_NAME = "retraitDiplome";

    private final RetraitDiplomeService retraitDiplomeService;

    public RetraitDiplomeResource(RetraitDiplomeService retraitDiplomeService) {
        this.retraitDiplomeService = retraitDiplomeService;
    }

    /**
     * POST  /retrait-diplomes : Create a new retraitDiplome.
     *
     * @param retraitDiplomeDTO the retraitDiplomeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new retraitDiplomeDTO, or with status 400 (Bad Request) if the retraitDiplome has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/retrait-diplomes")
    public ResponseEntity<RetraitDiplomeDTO> createRetraitDiplome(@Valid @RequestBody RetraitDiplomeDTO retraitDiplomeDTO) throws URISyntaxException {
        log.debug("REST request to save RetraitDiplome : {}", retraitDiplomeDTO);
        if (retraitDiplomeDTO.getId() != null) {
            throw new BadRequestAlertException("A new retraitDiplome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RetraitDiplomeDTO result = retraitDiplomeService.save(retraitDiplomeDTO);
        return ResponseEntity.created(new URI("/api/retrait-diplomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /retrait-diplomes : Updates an existing retraitDiplome.
     *
     * @param retraitDiplomeDTO the retraitDiplomeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated retraitDiplomeDTO,
     * or with status 400 (Bad Request) if the retraitDiplomeDTO is not valid,
     * or with status 500 (Internal Server Error) if the retraitDiplomeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/retrait-diplomes")
    public ResponseEntity<RetraitDiplomeDTO> updateRetraitDiplome(@Valid @RequestBody RetraitDiplomeDTO retraitDiplomeDTO) throws URISyntaxException {
        log.debug("REST request to update RetraitDiplome : {}", retraitDiplomeDTO);
        if (retraitDiplomeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RetraitDiplomeDTO result = retraitDiplomeService.save(retraitDiplomeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, retraitDiplomeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /retrait-diplomes : get all the retraitDiplomes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of retraitDiplomes in body
     */
    @GetMapping("/retrait-diplomes")
    public ResponseEntity<List<RetraitDiplomeDTO>> getAllRetraitDiplomes(Pageable pageable) {
        log.debug("REST request to get a page of RetraitDiplomes");
        Page<RetraitDiplomeDTO> page = retraitDiplomeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/retrait-diplomes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /retrait-diplomes/:id : get the "id" retraitDiplome.
     *
     * @param id the id of the retraitDiplomeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the retraitDiplomeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/retrait-diplomes/{id}")
    public ResponseEntity<RetraitDiplomeDTO> getRetraitDiplome(@PathVariable Long id) {
        log.debug("REST request to get RetraitDiplome : {}", id);
        Optional<RetraitDiplomeDTO> retraitDiplomeDTO = retraitDiplomeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(retraitDiplomeDTO);
    }

    /**
     * DELETE  /retrait-diplomes/:id : delete the "id" retraitDiplome.
     *
     * @param id the id of the retraitDiplomeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/retrait-diplomes/{id}")
    public ResponseEntity<Void> deleteRetraitDiplome(@PathVariable Long id) {
        log.debug("REST request to delete RetraitDiplome : {}", id);
        retraitDiplomeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
