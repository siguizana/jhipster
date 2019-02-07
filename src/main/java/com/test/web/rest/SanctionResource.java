package com.test.web.rest;
import com.test.service.SanctionService;
import com.test.web.rest.errors.BadRequestAlertException;
import com.test.web.rest.util.HeaderUtil;
import com.test.web.rest.util.PaginationUtil;
import com.test.service.dto.SanctionDTO;
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
 * REST controller for managing Sanction.
 */
@RestController
@RequestMapping("/api")
public class SanctionResource {

    private final Logger log = LoggerFactory.getLogger(SanctionResource.class);

    private static final String ENTITY_NAME = "sanction";

    private final SanctionService sanctionService;

    public SanctionResource(SanctionService sanctionService) {
        this.sanctionService = sanctionService;
    }

    /**
     * POST  /sanctions : Create a new sanction.
     *
     * @param sanctionDTO the sanctionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sanctionDTO, or with status 400 (Bad Request) if the sanction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sanctions")
    public ResponseEntity<SanctionDTO> createSanction(@Valid @RequestBody SanctionDTO sanctionDTO) throws URISyntaxException {
        log.debug("REST request to save Sanction : {}", sanctionDTO);
        if (sanctionDTO.getId() != null) {
            throw new BadRequestAlertException("A new sanction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SanctionDTO result = sanctionService.save(sanctionDTO);
        return ResponseEntity.created(new URI("/api/sanctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sanctions : Updates an existing sanction.
     *
     * @param sanctionDTO the sanctionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sanctionDTO,
     * or with status 400 (Bad Request) if the sanctionDTO is not valid,
     * or with status 500 (Internal Server Error) if the sanctionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sanctions")
    public ResponseEntity<SanctionDTO> updateSanction(@Valid @RequestBody SanctionDTO sanctionDTO) throws URISyntaxException {
        log.debug("REST request to update Sanction : {}", sanctionDTO);
        if (sanctionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SanctionDTO result = sanctionService.save(sanctionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, sanctionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sanctions : get all the sanctions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of sanctions in body
     */
    @GetMapping("/sanctions")
    public ResponseEntity<List<SanctionDTO>> getAllSanctions(Pageable pageable) {
        log.debug("REST request to get a page of Sanctions");
        Page<SanctionDTO> page = sanctionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sanctions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /sanctions/:id : get the "id" sanction.
     *
     * @param id the id of the sanctionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sanctionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sanctions/{id}")
    public ResponseEntity<SanctionDTO> getSanction(@PathVariable Long id) {
        log.debug("REST request to get Sanction : {}", id);
        Optional<SanctionDTO> sanctionDTO = sanctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sanctionDTO);
    }

    /**
     * DELETE  /sanctions/:id : delete the "id" sanction.
     *
     * @param id the id of the sanctionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sanctions/{id}")
    public ResponseEntity<Void> deleteSanction(@PathVariable Long id) {
        log.debug("REST request to delete Sanction : {}", id);
        sanctionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
