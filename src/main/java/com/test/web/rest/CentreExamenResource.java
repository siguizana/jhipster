package com.test.web.rest;
import com.test.service.CentreExamenService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.CentreExamenDTO;
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
 * REST controller for managing CentreExamen.
 */
@RestController
@RequestMapping("/api")
public class CentreExamenResource {

    private final Logger log = LoggerFactory.getLogger(CentreExamenResource.class);

    private static final String ENTITY_NAME = "centreExamen";

    private final CentreExamenService centreExamenService;

    public CentreExamenResource(CentreExamenService centreExamenService) {
        this.centreExamenService = centreExamenService;
    }

    /**
     * POST  /centre-examen : Create a new centreExamen.
     *
     * @param centreExamenDTO the centreExamenDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new centreExamenDTO, or with status 400 (Bad Request) if the centreExamen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/centre-examen")
    public ResponseEntity<CentreExamenDTO> createCentreExamen(@Valid @RequestBody CentreExamenDTO centreExamenDTO) throws URISyntaxException {
        log.debug("REST request to save CentreExamen : {}", centreExamenDTO);
        if (centreExamenDTO.getId() != null) {
            throw new BadRequestAlertException("A new centreExamen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CentreExamenDTO result = centreExamenService.save(centreExamenDTO);
        return ResponseEntity.created(new URI("/api/centre-examen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /centre-examen : Updates an existing centreExamen.
     *
     * @param centreExamenDTO the centreExamenDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated centreExamenDTO,
     * or with status 400 (Bad Request) if the centreExamenDTO is not valid,
     * or with status 500 (Internal Server Error) if the centreExamenDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/centre-examen")
    public ResponseEntity<CentreExamenDTO> updateCentreExamen(@Valid @RequestBody CentreExamenDTO centreExamenDTO) throws URISyntaxException {
        log.debug("REST request to update CentreExamen : {}", centreExamenDTO);
        if (centreExamenDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CentreExamenDTO result = centreExamenService.save(centreExamenDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, centreExamenDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /centre-examen : get all the centreExamen.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of centreExamen in body
     */
    @GetMapping("/centre-examen")
    public ResponseEntity<List<CentreExamenDTO>> getAllCentreExamen(Pageable pageable) {
        log.debug("REST request to get a page of CentreExamen");
        Page<CentreExamenDTO> page = centreExamenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/centre-examen");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /centre-examen/:id : get the "id" centreExamen.
     *
     * @param id the id of the centreExamenDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the centreExamenDTO, or with status 404 (Not Found)
     */
    @GetMapping("/centre-examen/{id}")
    public ResponseEntity<CentreExamenDTO> getCentreExamen(@PathVariable Long id) {
        log.debug("REST request to get CentreExamen : {}", id);
        Optional<CentreExamenDTO> centreExamenDTO = centreExamenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(centreExamenDTO);
    }

    /**
     * DELETE  /centre-examen/:id : delete the "id" centreExamen.
     *
     * @param id the id of the centreExamenDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/centre-examen/{id}")
    public ResponseEntity<Void> deleteCentreExamen(@PathVariable Long id) {
        log.debug("REST request to delete CentreExamen : {}", id);
        centreExamenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
