package com.test.web.rest;
import com.test.service.CentreCompositioJuryService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.CentreCompositioJuryDTO;
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
 * REST controller for managing CentreCompositioJury.
 */
@RestController
@RequestMapping("/api")
public class CentreCompositioJuryResource {

    private final Logger log = LoggerFactory.getLogger(CentreCompositioJuryResource.class);

    private static final String ENTITY_NAME = "centreCompositioJury";

    private final CentreCompositioJuryService centreCompositioJuryService;

    public CentreCompositioJuryResource(CentreCompositioJuryService centreCompositioJuryService) {
        this.centreCompositioJuryService = centreCompositioJuryService;
    }

    /**
     * POST  /centre-compositio-juries : Create a new centreCompositioJury.
     *
     * @param centreCompositioJuryDTO the centreCompositioJuryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new centreCompositioJuryDTO, or with status 400 (Bad Request) if the centreCompositioJury has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/centre-compositio-juries")
    public ResponseEntity<CentreCompositioJuryDTO> createCentreCompositioJury(@RequestBody CentreCompositioJuryDTO centreCompositioJuryDTO) throws URISyntaxException {
        log.debug("REST request to save CentreCompositioJury : {}", centreCompositioJuryDTO);
        if (centreCompositioJuryDTO.getId() != null) {
            throw new BadRequestAlertException("A new centreCompositioJury cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentreCompositioJuryDTO result = centreCompositioJuryService.save(centreCompositioJuryDTO);
        return ResponseEntity.created(new URI("/api/centre-compositio-juries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /centre-compositio-juries : Updates an existing centreCompositioJury.
     *
     * @param centreCompositioJuryDTO the centreCompositioJuryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated centreCompositioJuryDTO,
     * or with status 400 (Bad Request) if the centreCompositioJuryDTO is not valid,
     * or with status 500 (Internal Server Error) if the centreCompositioJuryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/centre-compositio-juries")
    public ResponseEntity<CentreCompositioJuryDTO> updateCentreCompositioJury(@RequestBody CentreCompositioJuryDTO centreCompositioJuryDTO) throws URISyntaxException {
        log.debug("REST request to update CentreCompositioJury : {}", centreCompositioJuryDTO);
        if (centreCompositioJuryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CentreCompositioJuryDTO result = centreCompositioJuryService.save(centreCompositioJuryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, centreCompositioJuryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /centre-compositio-juries : get all the centreCompositioJuries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of centreCompositioJuries in body
     */
    @GetMapping("/centre-compositio-juries")
    public ResponseEntity<List<CentreCompositioJuryDTO>> getAllCentreCompositioJuries(Pageable pageable) {
        log.debug("REST request to get a page of CentreCompositioJuries");
        Page<CentreCompositioJuryDTO> page = centreCompositioJuryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/centre-compositio-juries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /centre-compositio-juries/:id : get the "id" centreCompositioJury.
     *
     * @param id the id of the centreCompositioJuryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the centreCompositioJuryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/centre-compositio-juries/{id}")
    public ResponseEntity<CentreCompositioJuryDTO> getCentreCompositioJury(@PathVariable Long id) {
        log.debug("REST request to get CentreCompositioJury : {}", id);
        Optional<CentreCompositioJuryDTO> centreCompositioJuryDTO = centreCompositioJuryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centreCompositioJuryDTO);
    }

    /**
     * DELETE  /centre-compositio-juries/:id : delete the "id" centreCompositioJury.
     *
     * @param id the id of the centreCompositioJuryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/centre-compositio-juries/{id}")
    public ResponseEntity<Void> deleteCentreCompositioJury(@PathVariable Long id) {
        log.debug("REST request to delete CentreCompositioJury : {}", id);
        centreCompositioJuryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
