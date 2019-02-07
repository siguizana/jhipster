package com.test.web.rest;
import com.test.service.DeroulementScolariteService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.DeroulementScolariteDTO;
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
 * REST controller for managing DeroulementScolarite.
 */
@RestController
@RequestMapping("/api")
public class DeroulementScolariteResource {

    private final Logger log = LoggerFactory.getLogger(DeroulementScolariteResource.class);

    private static final String ENTITY_NAME = "deroulementScolarite";

    private final DeroulementScolariteService deroulementScolariteService;

    public DeroulementScolariteResource(DeroulementScolariteService deroulementScolariteService) {
        this.deroulementScolariteService = deroulementScolariteService;
    }

    /**
     * POST  /deroulement-scolarites : Create a new deroulementScolarite.
     *
     * @param deroulementScolariteDTO the deroulementScolariteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new deroulementScolariteDTO, or with status 400 (Bad Request) if the deroulementScolarite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/deroulement-scolarites")
    public ResponseEntity<DeroulementScolariteDTO> createDeroulementScolarite(@Valid @RequestBody DeroulementScolariteDTO deroulementScolariteDTO) throws URISyntaxException {
        log.debug("REST request to save DeroulementScolarite : {}", deroulementScolariteDTO);
        if (deroulementScolariteDTO.getId() != null) {
            throw new BadRequestAlertException("A new deroulementScolarite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DeroulementScolariteDTO result = deroulementScolariteService.save(deroulementScolariteDTO);
        return ResponseEntity.created(new URI("/api/deroulement-scolarites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deroulement-scolarites : Updates an existing deroulementScolarite.
     *
     * @param deroulementScolariteDTO the deroulementScolariteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated deroulementScolariteDTO,
     * or with status 400 (Bad Request) if the deroulementScolariteDTO is not valid,
     * or with status 500 (Internal Server Error) if the deroulementScolariteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/deroulement-scolarites")
    public ResponseEntity<DeroulementScolariteDTO> updateDeroulementScolarite(@Valid @RequestBody DeroulementScolariteDTO deroulementScolariteDTO) throws URISyntaxException {
        log.debug("REST request to update DeroulementScolarite : {}", deroulementScolariteDTO);
        if (deroulementScolariteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DeroulementScolariteDTO result = deroulementScolariteService.save(deroulementScolariteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, deroulementScolariteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deroulement-scolarites : get all the deroulementScolarites.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of deroulementScolarites in body
     */
    @GetMapping("/deroulement-scolarites")
    public ResponseEntity<List<DeroulementScolariteDTO>> getAllDeroulementScolarites(Pageable pageable) {
        log.debug("REST request to get a page of DeroulementScolarites");
        Page<DeroulementScolariteDTO> page = deroulementScolariteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deroulement-scolarites");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /deroulement-scolarites/:id : get the "id" deroulementScolarite.
     *
     * @param id the id of the deroulementScolariteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the deroulementScolariteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/deroulement-scolarites/{id}")
    public ResponseEntity<DeroulementScolariteDTO> getDeroulementScolarite(@PathVariable Long id) {
        log.debug("REST request to get DeroulementScolarite : {}", id);
        Optional<DeroulementScolariteDTO> deroulementScolariteDTO = deroulementScolariteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(deroulementScolariteDTO);
    }

    /**
     * DELETE  /deroulement-scolarites/:id : delete the "id" deroulementScolarite.
     *
     * @param id the id of the deroulementScolariteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/deroulement-scolarites/{id}")
    public ResponseEntity<Void> deleteDeroulementScolarite(@PathVariable Long id) {
        log.debug("REST request to delete DeroulementScolarite : {}", id);
        deroulementScolariteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
