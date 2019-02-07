package com.test.web.rest;
import com.test.service.HandicapeService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.HandicapeDTO;
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
 * REST controller for managing Handicape.
 */
@RestController
@RequestMapping("/api")
public class HandicapeResource {

    private final Logger log = LoggerFactory.getLogger(HandicapeResource.class);

    private static final String ENTITY_NAME = "handicape";

    private final HandicapeService handicapeService;

    public HandicapeResource(HandicapeService handicapeService) {
        this.handicapeService = handicapeService;
    }

    /**
     * POST  /handicapes : Create a new handicape.
     *
     * @param handicapeDTO the handicapeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new handicapeDTO, or with status 400 (Bad Request) if the handicape has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/handicapes")
    public ResponseEntity<HandicapeDTO> createHandicape(@Valid @RequestBody HandicapeDTO handicapeDTO) throws URISyntaxException {
        log.debug("REST request to save Handicape : {}", handicapeDTO);
        if (handicapeDTO.getId() != null) {
            throw new BadRequestAlertException("A new handicape cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HandicapeDTO result = handicapeService.save(handicapeDTO);
        return ResponseEntity.created(new URI("/api/handicapes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /handicapes : Updates an existing handicape.
     *
     * @param handicapeDTO the handicapeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated handicapeDTO,
     * or with status 400 (Bad Request) if the handicapeDTO is not valid,
     * or with status 500 (Internal Server Error) if the handicapeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/handicapes")
    public ResponseEntity<HandicapeDTO> updateHandicape(@Valid @RequestBody HandicapeDTO handicapeDTO) throws URISyntaxException {
        log.debug("REST request to update Handicape : {}", handicapeDTO);
        if (handicapeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        HandicapeDTO result = handicapeService.save(handicapeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, handicapeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /handicapes : get all the handicapes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of handicapes in body
     */
    @GetMapping("/handicapes")
    public ResponseEntity<List<HandicapeDTO>> getAllHandicapes(Pageable pageable) {
        log.debug("REST request to get a page of Handicapes");
        Page<HandicapeDTO> page = handicapeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/handicapes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /handicapes/:id : get the "id" handicape.
     *
     * @param id the id of the handicapeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the handicapeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/handicapes/{id}")
    public ResponseEntity<HandicapeDTO> getHandicape(@PathVariable Long id) {
        log.debug("REST request to get Handicape : {}", id);
        Optional<HandicapeDTO> handicapeDTO = handicapeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(handicapeDTO);
    }

    /**
     * DELETE  /handicapes/:id : delete the "id" handicape.
     *
     * @param id the id of the handicapeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/handicapes/{id}")
    public ResponseEntity<Void> deleteHandicape(@PathVariable Long id) {
        log.debug("REST request to delete Handicape : {}", id);
        handicapeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
