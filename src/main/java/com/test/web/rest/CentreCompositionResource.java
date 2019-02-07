package com.test.web.rest;
import com.test.service.CentreCompositionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.CentreCompositionDTO;
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
 * REST controller for managing CentreComposition.
 */
@RestController
@RequestMapping("/api")
public class CentreCompositionResource {

    private final Logger log = LoggerFactory.getLogger(CentreCompositionResource.class);

    private static final String ENTITY_NAME = "centreComposition";

    private final CentreCompositionService centreCompositionService;

    public CentreCompositionResource(CentreCompositionService centreCompositionService) {
        this.centreCompositionService = centreCompositionService;
    }

    /**
     * POST  /centre-compositions : Create a new centreComposition.
     *
     * @param centreCompositionDTO the centreCompositionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new centreCompositionDTO, or with status 400 (Bad Request) if the centreComposition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/centre-compositions")
    public ResponseEntity<CentreCompositionDTO> createCentreComposition(@Valid @RequestBody CentreCompositionDTO centreCompositionDTO) throws URISyntaxException {
        log.debug("REST request to save CentreComposition : {}", centreCompositionDTO);
        if (centreCompositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new centreComposition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentreCompositionDTO result = centreCompositionService.save(centreCompositionDTO);
        return ResponseEntity.created(new URI("/api/centre-compositions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /centre-compositions : Updates an existing centreComposition.
     *
     * @param centreCompositionDTO the centreCompositionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated centreCompositionDTO,
     * or with status 400 (Bad Request) if the centreCompositionDTO is not valid,
     * or with status 500 (Internal Server Error) if the centreCompositionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/centre-compositions")
    public ResponseEntity<CentreCompositionDTO> updateCentreComposition(@Valid @RequestBody CentreCompositionDTO centreCompositionDTO) throws URISyntaxException {
        log.debug("REST request to update CentreComposition : {}", centreCompositionDTO);
        if (centreCompositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CentreCompositionDTO result = centreCompositionService.save(centreCompositionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, centreCompositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /centre-compositions : get all the centreCompositions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of centreCompositions in body
     */
    @GetMapping("/centre-compositions")
    public ResponseEntity<List<CentreCompositionDTO>> getAllCentreCompositions(Pageable pageable) {
        log.debug("REST request to get a page of CentreCompositions");
        Page<CentreCompositionDTO> page = centreCompositionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/centre-compositions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /centre-compositions/:id : get the "id" centreComposition.
     *
     * @param id the id of the centreCompositionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the centreCompositionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/centre-compositions/{id}")
    public ResponseEntity<CentreCompositionDTO> getCentreComposition(@PathVariable Long id) {
        log.debug("REST request to get CentreComposition : {}", id);
        Optional<CentreCompositionDTO> centreCompositionDTO = centreCompositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centreCompositionDTO);
    }

    /**
     * DELETE  /centre-compositions/:id : delete the "id" centreComposition.
     *
     * @param id the id of the centreCompositionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/centre-compositions/{id}")
    public ResponseEntity<Void> deleteCentreComposition(@PathVariable Long id) {
        log.debug("REST request to delete CentreComposition : {}", id);
        centreCompositionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
